package commands;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.event.inventory.InventoryType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestDisposalCommand {

    private ServerMock server;

    @Before
    public void setUp() {
        server = TestUtility.setup();
    }

    @Test
    @DisplayName("Opens an inventory that has nothing in it")
    public void testInventory() {
        PlayerMock player = server.addPlayer();
        player.performCommand("disposal");
        player.assertInventoryView(InventoryType.CHEST);
    }

    @After
    public void tearDown() {
        TestUtility.tearDown();
    }

}