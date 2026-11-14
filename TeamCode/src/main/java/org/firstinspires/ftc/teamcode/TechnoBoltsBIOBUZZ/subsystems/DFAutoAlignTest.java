
package org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;


@TeleOp(
        name = "BioBuzz Auto Align Test",
        group = "TechnoBolts"
)

public class DFAutoAlignTest extends LinearOpMode {

    private DFAutoAlign autoAlign;

    private boolean trackingEnabled = false;
    private boolean isBlue = false;

    @Override
    public void runOpMode() {

        autoAlign = new DFAutoAlign();

        // Starting position: (144, 48)
        Pose2D startingPose = new Pose2D(
                DistanceUnit.INCH,
                144,
                48,
                AngleUnit.DEGREES,
                0
        );

        // Initialize hardware
        autoAlign.initialize(
                hardwareMap,
                startingPose
        );

        telemetry.addLine("BIOBUZZ AUTO ALIGN");
        telemetry.addLine("----------------------");
        telemetry.addLine("Pinpoint Initialized");
        telemetry.addLine("Starting Position: 144, 48");
        telemetry.addLine("Default Alliance: RED");
        telemetry.addLine("Waiting for start...");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        boolean previousRB = false;
        boolean previousA = false;
        boolean previousX = false;
        boolean previousY = false;

        while (opModeIsActive()) {

            // ==================================
            // UPDATE LOCALIZATION
            // ==================================

            autoAlign.updateLocalization();

            // ==================================
            // RIGHT BUMPER: TRACKING TOGGLE
            // ==================================

            if (gamepad1.right_bumper && !previousRB) {

                trackingEnabled = !trackingEnabled;
            }

            previousRB = gamepad1.right_bumper;

            // ==================================
            // A: SWITCH ALLIANCE
            // ==================================

            if (gamepad1.a && !previousA) {

                isBlue = !isBlue;

                autoAlign.setAlliance(isBlue);
            }

            previousA = gamepad1.a;

            // ==================================
            // X: RESET PINPOINT
            // ==================================

            if (gamepad1.x && !previousX) {

                Pose2D resetPose = new Pose2D(
                        DistanceUnit.INCH,
                        144,
                        48,
                        AngleUnit.DEGREES,
                        0
                );

                autoAlign.reset(resetPose);

                trackingEnabled = false;
            }

            previousX = gamepad1.x;

            // ==================================
            // Y: CENTER TURRET
            // ==================================

            if (gamepad1.y && !previousY) {

                autoAlign.centerTurret();
            }

            previousY = gamepad1.y;

            // ==================================
            // RUN AUTO ALIGNMENT
            // ==================================

            if (trackingEnabled) {

                autoAlign.autoAlign();
            }

            // ==================================
            // TELEMETRY
            // ==================================

            telemetry.addLine(
                    "===== BIOBUZZ AUTO ALIGN ====="
            );

            telemetry.addLine("--- SYSTEM ---");

            telemetry.addData(
                    "Tracking",
                    trackingEnabled ? "ON" : "OFF"
            );

            telemetry.addData(
                    "Alliance",
                    isBlue ? "BLUE" : "RED"
            );

            telemetry.addLine("--- PINPOINT ---");

            telemetry.addData(
                    "X",
                    "%.2f",
                    autoAlign.getX()
            );

            telemetry.addData(
                    "Y",
                    "%.2f",
                    autoAlign.getY()
            );

            telemetry.addData(
                    "Heading",
                    "%.2f",
                    autoAlign.getHeading()
            );

            telemetry.addLine("--- FIELD ---");

            telemetry.addData(
                    "Midfield X",
                    "72.0"
            );

            telemetry.addData(
                    "Crossed Midfield",
                    autoAlign.hasCrossedMidfield()
            );

            telemetry.addData(
                    "Selected Goal",
                    "(%.1f, %.1f)",
                    autoAlign.getGoalX(),
                    autoAlign.getGoalY()
            );

            telemetry.addLine("--- TURRET ---");

            telemetry.addData(
                    "Target Angle",
                    "%.2f degrees",
                    autoAlign.getCalculatedTargetAngle()
            );

            telemetry.addData(
                    "Turret Angle",
                    "%.2f degrees",
                    autoAlign.getTurretAngle()
            );

            telemetry.addData(
                    "Servo Position",
                    "%.4f",
                    autoAlign.getServoPosition()
            );

            telemetry.addData(
                    "Target Servo Position",
                    "%.4f",
                    autoAlign.getTargetServoPosition()
            );

            telemetry.addLine("--- CONTROLS ---");
            telemetry.addLine("RB: Toggle Tracking");
            telemetry.addLine("A: Switch Alliance");
            telemetry.addLine("X: Reset Pinpoint");
            telemetry.addLine("Y: Center Turret");

            telemetry.update();
        }

        autoAlign.stop();
    }
}
