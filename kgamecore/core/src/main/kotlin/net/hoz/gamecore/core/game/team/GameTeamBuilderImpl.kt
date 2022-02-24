package net.hoz.gamecore.core.game.team

import com.iamceph.resulter.core.DataResultable
import net.hoz.gamecore.api.game.team.GameTeam
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.screamingsandals.lib.world.LocationHolder

class GameTeamBuilderImpl(private var name: String) : GameTeam.Builder {
    private var color: NamedTextColor? = null
    private var spawn: LocationHolder? = null
    private var target: LocationHolder? = null
    private var maxPlayers: Int? = null

    override fun name(): String = name

    override fun name(name: String): GameTeam.Builder {
        this.name = name
        return this
    }

    override fun color(): NamedTextColor? = color

    override fun color(color: NamedTextColor): GameTeam.Builder {
        this.color = color
        return this
    }

    override fun coloredName(): Component {
        val name = name ?: return Component.empty()
        val color = color ?: return Component.empty()
        return Component.text(name).color(color)
    }

    override fun spawn(): LocationHolder? = spawn

    override fun spawn(spawn: LocationHolder): GameTeam.Builder {
        this.spawn = spawn
        return this
    }

    override fun target(): LocationHolder? = target

    override fun target(target: LocationHolder): GameTeam.Builder {
        this.target = target
        return this
    }

    override fun maxPlayers(): Int? = maxPlayers

    override fun maxPlayers(maxPlayers: Int): GameTeam.Builder {
        this.maxPlayers = maxPlayers
        return this
    }

    override fun build(): DataResultable<GameTeam> {
        val color = this.color ?: return DataResultable.fail("Color is not defined.")
        val spawn = this.spawn ?: return DataResultable.fail("Spawn is not defined.")
        val maxPlayers = this.maxPlayers ?: return DataResultable.fail("Max players are not defined.")

        return DataResultable.ok(GameTeamImpl(name, color, spawn, target, maxPlayers))
    }
}