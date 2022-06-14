package net.hoz.gamecore.core.service.manager

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.kotlin.dataResultable
import com.iamceph.resulter.kotlin.resultable
import mu.KotlinLogging
import net.hoz.api.data.GameType
import net.hoz.api.data.game.*
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import net.hoz.gamecore.api.service.GameManager
import net.hoz.gamecore.api.util.fromProto
import net.hoz.gamecore.core.game.frame.builder.GameBuilderImpl
import net.hoz.gamecore.core.game.world.GameWorldBuilderImpl
import org.screamingsandals.lib.entity.type.EntityTypeHolder
import org.screamingsandals.lib.world.LocationMapper
import java.util.*

private val log = KotlinLogging.logger { }

class GameManagerBuildersImpl(
    private val manager: GameManager
) : GameManager.Builders {
    private val builders = mutableMapOf<UUID, GameBuilder>()

    override fun all(): List<GameBuilder> = builders.values.toList()

    override fun create(name: String, config: GameConfig, type: GameType): DataResultable<GameBuilder> {
        if (builders.values.firstOrNull { it.name() == name } != null) {
            return DataResultable.fail("Builder already exists.")
        }

        return dataResultable {
            GameBuilderImpl(manager, UUID.randomUUID(), name, type, config)
                .also {
                    builders[it.uuid] = it
                }
        }
    }

    override fun from(data: ProtoGameFrame): DataResultable<GameBuilder> {
        val id = UUID.fromString(data.uuid)
        if (builders[id] != null) {
            return DataResultable.fail("Game already loaded.")
        }

        val config = manager.backend.oneConfig(data.configName)
        if (config.isFail) {
            return config.transform()
        }

        val builder = GameBuilderImpl(manager, id, data.name, data.type, config.data(), true)
        val convertedTeams = convertTeams(builder, data.teamsList)
        if (convertedTeams.isFail) {
            return convertedTeams.transform()
        }
        val convertedSpawners = convertSpawners(builder, data.spawnersList)
        if (convertedSpawners.isFail) {
            return convertedSpawners.transform()
        }
        val convertedStores = convertStores(builder, data.storesList)
        if (convertedStores.isFail) {
            return convertedStores.transform()
        }

        builder.world = GameWorldBuilderImpl.fromProto(data.world)

        return DataResultable.ok(builder)
    }

    override fun from(frame: GameFrame): GameBuilder {
        TODO("Not yet implemented")
    }

    override fun remove(uuid: UUID): Boolean {
        val it = builders[uuid]
            ?: return false
        it.manage.destroy()

        builders.remove(uuid)
        return true
    }

    override fun has(uuid: UUID): Boolean = builders[uuid] != null

    override fun find(name: String): GameBuilder? = builders.values
        .firstOrNull { it.name() == name }

    fun convertTeams(builder: GameBuilder, teams: List<ProtoGameTeam>): GroupedResultable {
        return GroupedResultable.of(teams.map {
            val id = it.name
            return@map resultable {
                builder.teams.add(id) {
                    color = it.color.fromProto()
                    spawn = LocationMapper.resolve(it.spawn).orElse(null)
                    target = LocationMapper.resolve(it.target).orElse(null)
                    maxPlayers = it.maxPlayers
                }
            }
        })
    }

    fun convertSpawners(builder: GameBuilder, spawners: List<ProtoGameSpawner>): GroupedResultable {
        return GroupedResultable.of(spawners.map {
            val id = UUID.fromString(it.id)
            val convertedTypes = convertSpawnerTypes(it.typesList)

            return@map resultable {
                builder.spawners.add(id) {
                    if (it.team.isNotEmpty()) {
                        team = it.team
                    }
                    location = LocationMapper.resolve(it.location).orElseThrow()
                    useHolograms = it.useHolograms
                    useGlobalValues = it.useGlobalValues
                    types.addAll(convertedTypes)
                }
            }
        })
    }

    fun convertStores(builder: GameBuilder, spawners: List<ProtoGameStore>): GroupedResultable {
        return GroupedResultable.of(spawners.map {
            val id = it.name
            val resolvedHolder = manager.backend.oneStore(it.holderId)
            if (resolvedHolder.isFail) {
                return@map resolvedHolder
            }

            return@map resultable {
                builder.stores.add(id) {
                    if (it.teamId.isNotEmpty()) {
                        team = it.teamId
                    }
                    location = LocationMapper.resolve(it.location).orElseThrow()
                    holder = resolvedHolder.data()
                    entityType = EntityTypeHolder.of(it.entityType)
                }
            }
        })
    }

    fun convertSpawnerTypes(types: List<String>): List<GameSpawnerType> {
        val convertedTypes = types.map {
            manager.backend.oneSpawner(it)
                .flatMap { spawner -> GameSpawnerType.of(spawner) }
        }

        if (convertedTypes.any { it.isFail }) {
            val grouped = GroupedResultable.of(convertedTypes)
            log.warn { "Cannot convert spawner types - ${grouped.messages()}" }
            return listOf()
        }

        return convertedTypes.map { it.data() }
    }
}