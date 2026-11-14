package org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ.Auto;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutoBlueFullB {

    private final Follower follower;
    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;
    public final ElapsedTime pathTimer = new ElapsedTime();

    private int pathState = 0;

    // Constructor matching the AutoSelector initialization
    public AutoBlueFullB(Follower follower, HardwareMap hardwareMap, Telemetry telemetry) {
        this.follower = follower;
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    /**
     * Called when Autonomous starts (from AutoSelector.start())
     */
    public void start() {
        pathState = 0;
        pathTimer.reset();

        // Build and follow your first Pedro Pathing path here
        // follower.followPath(...);
    }

    /**
     * Called repeatedly inside the OpMode loop (from AutoSelector.loop())
     */
    public void update() {
        // Keep Pedro Pathing active
        follower.update();

        // Autonomous State Machine
        switch (pathState) {
            case 0:
                // Example state check
                if (!follower.isBusy()) {
                    pathState = 1;
                    pathTimer.reset();
                }
                break;

            case 1:
                // Next step in pathing or mechanism action
                break;

            default:
                break;
        }

        // Telemetry specific to this path
        telemetry.addData("Red Short A State", pathState);
        telemetry.addData("Path Timer", pathTimer.seconds());
    }
}





//1: Shoot preloaded pollen into blue side
//2: Pick up 4 pollen from the flower right next to us and go to other side
//3: Shoot 4 into other side
//4: Go to pick up pollen from the flower on the side and come back and shoot
//5: Go to park zone (or don't - not necessary)