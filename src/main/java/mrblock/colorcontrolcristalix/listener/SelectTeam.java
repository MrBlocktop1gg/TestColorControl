package mrblock.colorcontrolcristalix.listener;

import mrblock.colorcontrolcristalix.util.Alarm;
import mrblock.colorcontrolcristalix.util.NameItems;
import mrblock.colorcontrolcristalix.game.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SelectTeam implements Listener {
    private final byte teamSize;
    private Block teamBlock;
    private byte playersBlueTeam;
    private byte playersRedTeam;
    protected FileConfiguration config;
    protected Scoreboard scoreboard;

    TeamColor blueTeam = TeamColor.BLUE_TEAM;
    TeamColor redTeam = TeamColor.RED_TEAM;
    TeamColor teamColor;
    public SelectTeam(Plugin plugin) {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        config = plugin.getConfig();

        playersBlueTeam = 0;
        playersRedTeam = 0;
        teamSize = 1;
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
            if (playersBlueTeam >= teamSize) {
                Alarm.alarmTitle("", "Команда заполнена!",10,20,10);
                return;
            }
            setPlayerToTeam(player, blueTeam);
            playersBlueTeam++;
            playersRedTeam--;
            event.setCancelled(true);
        }
        if (event.getCurrentItem().getType() == Material.RED_GLAZED_TERRACOTTA) {
            if (playersRedTeam >= teamSize) {
                Alarm.alarmTitle("", "Команда заполнена!",10,20,10);
                return;
            }
            setPlayerToTeam(player, redTeam);
            playersRedTeam++;
            playersBlueTeam--;
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void playerLeaveServer(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Team teamLeave = scoreboard.getTeam(teamColor.name());
        if (TeamColor.BLUE_TEAM == blueTeam) {
            teamLeave.removePlayer(player);
            playersBlueTeam--;
        }
        if (TeamColor.RED_TEAM == redTeam) {
            teamLeave.removePlayer(player);
            playersRedTeam--;
        }
    }
    public void setPlayerToTeam(Player player, TeamColor color) {
        Team teams = scoreboard.getTeam(teamColor.name());

        if (teams == null)
            teams = scoreboard.registerNewTeam(teamColor.name());

        teams.addPlayer(player);

        if (color == TeamColor.BLUE_TEAM) {
            teams.setDisplayName("§1§l" + teamColor.name());
            teams.setPrefix("§1§l" + teamColor.name());
            player.sendTitle("Вы выбрали команду", "§1§l" + teamColor.name());
        }
        if (color == TeamColor.RED_TEAM) {
            teams.setDisplayName("§c§l" + teamColor.name());
            teams.setPrefix("§c§l" + teamColor.name());
            player.sendTitle("Вы выбрали команду", "§c§l" + teamColor.name());
        }
    }

    @EventHandler
    public void seizureTerritory(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.CONCRETE) {
            setColor(teamColor);
        }
        if (block.getType() == Material.CONCRETE) {
            setColor(teamColor);
        }
    }
    public void setColor(TeamColor teamColor) {
        this.teamColor = teamColor;

        switch (teamColor) {
            case BLUE_TEAM:
                teamBlock.setType(Material.CONCRETE);
                teamBlock.setData((byte) 11);
                break;
            case RED_TEAM:
                teamBlock.setType(Material.CONCRETE);
                teamBlock.setData((byte) 14);
                break;
        }
    }
}
