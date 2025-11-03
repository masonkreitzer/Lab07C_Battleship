import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 *Unit test for the Ship class.
 */
public class ShipTest {

    @Test
    void testShipCreationAndHits() {
        Ship ship = new Ship(3);
        assertEquals(3, ship.getSize());

        // Add coordinates
        ship.addCoordinate(0, 0);
        ship.addCoordinate(0, 1);
        ship.addCoordinate(0, 2);
        assertEquals(3, ship.getCoordinates().size());

        // Test hits
        assertFalse(ship.isSunk());
        ship.registerHit();
        ship.registerHit();
        assertFalse(ship.isSunk());
        ship.registerHit();
        assertTrue(ship.isSunk());
    }
}
