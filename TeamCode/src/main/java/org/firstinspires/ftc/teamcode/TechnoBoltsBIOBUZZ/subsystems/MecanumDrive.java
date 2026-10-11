package org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Hardware-only mecanum drivetrain subsystem.
 *
 * <p>This class deliberately contains no game-mechanism, sensor, Pedro, or OpMode code.
 * It is safe to reuse from a simple TeleOp now and from a Pedro-based TeleOp later.</p>
 */
public final class MecanumDrive {
    private final DcMotorEx frontLeft;
    private final DcMotorEx frontRight;
    private final DcMotorEx backLeft;
    private final DcMotorEx backRight;

    public MecanumDrive(
            HardwareMap hardwareMap,
            String frontLeftName,
            String frontRightName,
            String backLeftName,
            String backRightName,
            DcMotorSimple.Direction frontLeftDirection,
            DcMotorSimple.Direction frontRightDirection,
            DcMotorSimple.Direction backLeftDirection,
            DcMotorSimple.Direction backRightDirection) {
        frontLeft = hardwareMap.get(DcMotorEx.class, frontLeftName);
        frontRight = hardwareMap.get(DcMotorEx.class, frontRightName);
        backLeft = hardwareMap.get(DcMotorEx.class, backLeftName);
        backRight = hardwareMap.get(DcMotorEx.class, backRightName);

        frontLeft.setDirection(frontLeftDirection);
        frontRight.setDirection(frontRightDirection);
        backLeft.setDirection(backLeftDirection);
        backRight.setDirection(backRightDirection);

        setBrakeMode(true);
    }

    /** Drives robot-relative mecanum with each input normally in [-1, 1]. */
    public void driveRobotCentric(double forward, double strafe, double turn) {
        double frontLeftPower = forward + strafe + turn;
        double frontRightPower = forward - strafe - turn;
        double backLeftPower = forward - strafe + turn;
        double backRightPower = forward + strafe - turn;

        double largestMagnitude = Math.max(
                1.0,
                Math.max(Math.abs(frontLeftPower), Math.max(
                        Math.abs(frontRightPower), Math.max(
                                Math.abs(backLeftPower), Math.abs(backRightPower)))));

        setMotorPowers(
                frontLeftPower / largestMagnitude,
                frontRightPower / largestMagnitude,
                backLeftPower / largestMagnitude,
                backRightPower / largestMagnitude);
    }

    public void setMotorPowers(double frontLeftPower, double frontRightPower,
                               double backLeftPower, double backRightPower) {
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }

    public void stop() {
        setMotorPowers(0, 0, 0, 0);
    }

    public void setBrakeMode(boolean enabled) {
        DcMotor.ZeroPowerBehavior behavior = enabled
                ? DcMotor.ZeroPowerBehavior.BRAKE
                : DcMotor.ZeroPowerBehavior.FLOAT;
        frontLeft.setZeroPowerBehavior(behavior);
        frontRight.setZeroPowerBehavior(behavior);
        backLeft.setZeroPowerBehavior(behavior);
        backRight.setZeroPowerBehavior(behavior);
    }
}
