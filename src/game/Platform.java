package game;

import city.cs.engine.*;
import java.awt.Color;
import org.jbox2d.common.Vec2;

/**
 * A static platform that the player and enemies can stand on.
 * The collision shape is a fixed-size box; the visual image is scaled to match.
 * Fill and outline colours are used in the debug view.
 */
public class Platform extends StaticBody {

    /**
     * Creates a platform with a custom image.
     *
     * @param world      the physics world
     * @param halfWidth  half the width of the collision box
     * @param halfHeight half the height of the collision box
     * @param fill       fill colour shown in the debug view
     * @param line       outline colour shown in the debug view
     * @param imagePath  path to the platform image file
     */
    public Platform(World world, float halfWidth, float halfHeight, Color fill, Color line, String imagePath) {
        super(world, new BoxShape(3f, 0.7f));

        setFillColor(fill);
        setLineColor(line);

        // Scale the image height to match the physics body
        float imageHeight = halfHeight * 2f * 4.6f;
        float imageWidth = halfWidth * 2f;
        addImage(new BodyImage(imagePath, imageHeight));
    }

    /**
     * Creates a platform using the default platform image.
     *
     * @param world      the physics world
     * @param halfWidth  half the width of the collision box
     * @param halfHeight half the height of the collision box
     * @param fill       fill colour shown in the debug view
     * @param line       outline colour shown in the debug view
     */
    public Platform(World world, float halfWidth, float halfHeight, Color fill, Color line) {
        this(world, halfWidth, halfHeight, fill, line, "data/platformE.png");
    }
}
