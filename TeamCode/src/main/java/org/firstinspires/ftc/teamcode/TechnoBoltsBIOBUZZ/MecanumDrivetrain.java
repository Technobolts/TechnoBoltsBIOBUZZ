package org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDrivetrain {

    DcMotorEx frontLeft = null;
    DcMotorEx frontRight = null;
    DcMotorEx backLeft = null;
    DcMotorEx backRight = null;

    public void init(HardwareMap hwMap) {
        frontLeft = hwMap.get(DcMotorEx.class, "front-left");
        frontRight = hwMap.get(DcMotorEx.class, "front-right");
        backLeft = hwMap.get(DcMotorEx.class, "back-left");
        backRight = hwMap.get(DcMotorEx.class, "back-right");

        // Left side motors are mounted mirrored, so reverse them.
        // If your robot drives backwards, flip these to the right side instead.
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);


        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void drive(double x, double y, double turn) {

        double theta = Math.atan2(y, x);
        double power = Math.hypot(x, y);

        double sin = Math.sin(theta - Math.PI / 4);
        double cos = Math.cos(theta - Math.PI / 4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        // Diagonal pairs share the same term: FL/BR use cos, FR/BL use sin
        double leftFrontPower  = power * cos / max + turn;
        double rightFrontPower = power * sin / max - turn;
        double leftBackPower   = power * sin / max + turn;
        double rightBackPower  = power * cos / max - turn;

        // Scale everything down if any motor would exceed full power
        if ((power + Math.abs(turn)) > 1) {
            double scale = power + Math.abs(turn);
            leftFrontPower  /= scale;
            rightFrontPower /= scale;
            leftBackPower   /= scale;
            rightBackPower  /= scale;
        }

        frontLeft.setPower(leftFrontPower);
        frontRight.setPower(rightFrontPower);
        backLeft.setPower(leftBackPower);
        backRight.setPower(rightBackPower);
    }
}