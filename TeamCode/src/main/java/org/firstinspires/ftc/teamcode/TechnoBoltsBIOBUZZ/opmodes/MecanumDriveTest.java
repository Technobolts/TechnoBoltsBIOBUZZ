package org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ.subsystems.MecanumDrive;

/** Temporary drivetrain-only TeleOp while mechanism hardware is unavailable. */
@TeleOp(name = "BIOBUZZ: Mecanum Drive Test", group = "BIOBUZZ")
public class MecanumDriveTest extends OpMode {
    /*
     * TODO(BIOBUZZ-CALIBRATE): Replace every hardware name after running the
     * Mecanum Tuner and configuring the robot in the Driver Station.
     * DECODE values retained for reference only:
     *   front-left, front-right, back-left, back-right
     */
    private static final String FRONT_LEFT_NAME = "front-left";
    private static final String FRONT_RIGHT_NAME = "front-right";
    private static final String BACK_LEFT_NAME = "back-left";
    private static final String BACK_RIGHT_NAME = "back-right";

    /*
     * TODO(BIOBUZZ-CALIBRATE): Verify each direction with the Mecanum Tuner.
     * These active values preserve the original BIOBUZZ starter TeleOp behavior.
     *
     * DECODE reference values retained as comments only:
     *   // front-left=FORWARD, back-left=FORWARD,
     *   // front-right=REVERSE, back-right=REVERSE
     */
    private static final DcMotorSimple.Direction FRONT_LEFT_DIRECTION = DcMotorSimple.Direction.REVERSE;
    private static final DcMotorSimple.Direction FRONT_RIGHT_DIRECTION = DcMotorSimple.Direction.FORWARD;
    private static final DcMotorSimple.Direction BACK_LEFT_DIRECTION = DcMotorSimple.Direction.REVERSE;
    private static final DcMotorSimple.Direction BACK_RIGHT_DIRECTION = DcMotorSimple.Direction.FORWARD;

    private MecanumDrive drivetrain;

    @Override
    public void init() {
        drivetrain = new MecanumDrive(
                hardwareMap,
                FRONT_LEFT_NAME, FRONT_RIGHT_NAME, BACK_LEFT_NAME, BACK_RIGHT_NAME,
                FRONT_LEFT_DIRECTION, FRONT_RIGHT_DIRECTION,
                BACK_LEFT_DIRECTION, BACK_RIGHT_DIRECTION);
        telemetry.addLine("Drivetrain initialized. Confirm wheel directions before driving.");
    }


    @Override
    public void loop() {
        drivetrain.driveRobotCentric(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x);
    }

    @Override
    public void stop() {
        drivetrain.stop();
    }
}
