package mrblock.colorcontrolcristalix.listener;

import mrblock.colorcontrolcristalix.NameItems;
import mrblock.colorcontrolcristalix.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class SelectTeam implements Listener {
    private Map<Player, TeamColor> playerTeams;
    protected FileConfiguration config;
    protected Scoreboard scoreboard;
    public int teamSize;
    public TeamColor color;
    public Block teamBlock;
    public int maxPlayers = 1;

    TeamColor teamColor;
    public SelectTeam(Plugin plugin) {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        config = plugin.getConfig();
        playerTeams = new HashMap<>();

        teamSize = 1;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }
    public void setTeamColor(TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    @EventHandler
    public void clickItemCompass(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = Bukkit.createInventory(player, 9, "§1§lВыбор §c§lКоманды!");
        String itemPathBlue = "items.player_select_team.blue_glazed_terracotta";
        String itemPathRed = "items.player_select_team.red_glazed_terracotta";

        ItemStack blue_glazed_terracotta = NameItems.createItem(
                Material.BLUE_GLAZED_TERRACOTTA,
                1,
                config.getString(itemPathBlue + ".name"));

        ItemStack red_glazed_terracotta = NameItems.createItem(
                Material.RED_GLAZED_TERRACOTTA,
                1,
                config.getString(itemPathRed + ".name"));


        if (player.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
            player.openInventory(inventory);

            inventory.setItem(2, blue_glazed_terracotta);
            inventory.setItem(6, red_glazed_terracotta);
        }
    }

    @EventHandler
    public void selectTeam(InventoryClickEvent event) {
        Player player = Bukkit.getPlayer(event.getWhoClicked().getName());
        if (event.getCurrentItem().getType() == Material.BLUE_GLAZED_TERRACOTTA) {
            setPlayerToTeam(player, TeamColor.BLUE_TEAM);
          //  playerTeams.put(player, TeamColor.BLUE_TEAM);
            event.setCancelled(true);
        }
        if (event.getCurrentItem().getType() == Material.RED_GLAZED_TERRACOTTA) {
            setPlayerToTeam(player, TeamColor.RED_TEAM);
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void playerLeaveServer(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Team teamLeave = scoreboard.getTeam(teamColor.name());
        teamLeave.removePlayer(player);

    }
    public void setPlayerToTeam(Player player, TeamColor team) {
        Team teams = scoreboard.getTeam(team.name());

        if (teams == null)
            teams = scoreboard.registerNewTeam(team.name());

        teams.addPlayer(player);
        teams.setDisplayName("§1§l" + team.name());
        player.sendTitle("Вы выбрали команду", "§1§l" + team.name());

        team.name().toString();
    }

    @Override
    public String toString() {
        String nameTeam = "";
        if (color == TeamColor.BLUE_TEAM) nameTeam = "§9СИНИЙ";
        if (color == TeamColor.RED_TEAM) nameTeam = "§cКРАСНЫЙ";
        return nameTeam;
    }
}
