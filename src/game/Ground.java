package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * A static ground tile that forms the floor of each level.
 * Three Ground objects are placed side by side to span the full level width.
 * The image is offset vertically so it aligns visually with the collision box.
 */
public class Ground extends StaticBody {

    /**
     * Creates a ground tile with a physics body and a visual image.
     *
     * @param world             the physics world
     * @param halfWidth         half the width of the collision box
     * @param visualHalfHeight  half the height of the collision box
     * @param imagePath         path to the ground image file
     * @param imageHeight       height of the displayed image
     * @param imageYOffset      vertical offset to align the image with the collision box
     */
    public Ground(World world,
                  float halfWidth,
                  float visualHalfHeight,
                  String imagePath,
                  float imageHeight,
                  float imageYOffset) {

        super(world, new BoxShape(halfWidth, visualHalfHeight));

        // Attach the image and shift it so it lines up with the physics body
        AttachedImage sprite = addImage(new BodyImage(imagePath, imageHeight));
        sprite.setOffset(new Vec2(0f, imageYOffset));
    }
}
