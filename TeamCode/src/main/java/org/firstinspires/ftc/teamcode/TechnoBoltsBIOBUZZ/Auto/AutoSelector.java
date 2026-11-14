package org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ.Auto;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Auto Selector", group = "Autonomous")
public class AutoSelector extends OpMode {

    // Enums for alliance and auto variations
    enum Alliance { RED, BLUE }
    enum AutoType { SHORT_A, FULL_A, SHORT_B, FULL_B }

    // Selection variables with defaults
    private Alliance alliance = Alliance.RED;
    private AutoType autoType = AutoType.SHORT_A;

    // Debouncing flag to prevent rapid toggling
    private boolean lastGamepad2X = false;

    // Hardware follower
    private Follower follower;

    // Sub-auto instances for Red Alliance
    private AutoRedShortA redShortA;
    private AutoRedFullA  redFullA;
    private AutoRedShortB redShortB;
    private AutoRedFullB  redFullB;

    // Sub-auto instances for Blue Alliance
    private AutoBlueShortA blueShortA;
    private AutoBlueFullA  blueFullA;
    private AutoBlueShortB blueShortB;
    private AutoBlueFullB  blueFullB;

    @Override
    public void init() {
//        follower = Constants.createFollower(hardwareMap);

        // Instantiate all 8 auto path options
        redShortA  = new AutoRedShortA(follower, hardwareMap, telemetry);
        redFullA   = new AutoRedFullA(follower, hardwareMap, telemetry);
        redShortB  = new AutoRedShortB(follower, hardwareMap, telemetry);
        redFullB   = new AutoRedFullB(follower, hardwareMap, telemetry);

        blueShortA = new AutoBlueShortA(follower, hardwareMap, telemetry);
        blueFullA  = new AutoBlueFullA(follower, hardwareMap, telemetry);
        blueShortB = new AutoBlueShortB(follower, hardwareMap, telemetry);
        blueFullB  = new AutoBlueFullB(follower, hardwareMap, telemetry);
    }

    @Override
    public void init_loop() {
        telemetry.addLine("=== AUTO SELECTION ===");
        telemetry.addLine("Press X to TOGGLE Alliance (RED / BLUE)");
        telemetry.addLine("Press A: Short A  |  Press B: Full A");
        telemetry.addLine("Press Y: Short B  |  Press D-Pad Up: Full B");
        telemetry.addLine("---------------------------------------------");

        // Toggle Alliance (Red / Blue) on Gamepad 2 X button
        if (gamepad2.x && !lastGamepad2X) {
            alliance = (alliance == Alliance.RED) ? Alliance.BLUE : Alliance.RED;
        }
        lastGamepad2X = gamepad2.x;

        // Select specific path variation on Gamepad 2
        if (gamepad2.a) autoType = AutoType.SHORT_A;
        if (gamepad2.b) autoType = AutoType.FULL_A;
        if (gamepad2.y) autoType = AutoType.SHORT_B;
        if (gamepad2.dpad_up) autoType = AutoType.FULL_B;

        // Telemetry feedback during init
        telemetry.addData("Selected Alliance", alliance);
        telemetry.addData("Selected Path", autoType);
        telemetry.addLine("---------------------------------------------");
        telemetry.update();
    }

    @Override
    public void start() {
        // Trigger the start method of the chosen auto routine
        if (alliance == Alliance.RED) {
            switch (autoType) {
                case SHORT_A: redShortA.start(); break;
                case FULL_A:  redFullA.start();  break;
                case SHORT_B: redShortB.start(); break;
                case FULL_B:  redFullB.start();  break;
            }
        } else { // BLUE
            switch (autoType) {
                case SHORT_A: blueShortA.start(); break;
                case FULL_A:  blueFullA.start();  break;
                case SHORT_B: blueShortB.start(); break;
                case FULL_B:  blueFullB.start();  break;
            }
        }
    }

    @Override
    public void loop() {
        // Update the active auto class and stream position telemetry
        if (alliance == Alliance.RED) {
            switch (autoType) {
                case SHORT_A: redShortA.update(); break;
                case FULL_A:  redFullA.update();  break;
                case SHORT_B: redShortB.update(); break;
                case FULL_B:  redFullB.update();  break;
            }
        } else { // BLUE
            switch (autoType) {
                case SHORT_A: blueShortA.update(); break;
                case FULL_A:  blueFullA.update();  break;
                case SHORT_B: blueShortB.update(); break;
                case FULL_B:  blueFullB.update();  break;
            }
        }

        // Live robot localization data on Driver Station
//        telemetry.addData("X Position", follower.getPose().getX());
//        telemetry.addData("Y Position", follower.getPose().getY());
//        telemetry.addData("Heading (Deg)", Math.toDegrees(follower.getPose().getHeading()));
//        telemetry.update();
    }
}