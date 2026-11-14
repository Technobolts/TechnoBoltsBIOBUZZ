package org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Outtake Control", group = "TeleOp")
public class OuttakeTestRun extends OpMode {

    private DcMotorEx leftOuttake;
    private DcMotorEx rightOuttake;

    double RV = 0;
    double LV = 0;

    double [] stepSizes = {1000.0, 100.0, 10.0, 1.0, 0.1};

    int stepIndex = 1;

    @Override
    public void init() {
        leftOuttake = hardwareMap.get(DcMotorEx.class, "leftOuttake");
        rightOuttake = hardwareMap.get(DcMotorEx.class, "rightOuttake");
    }

    @Override
    public void loop() {

        double tarVelocityRight = RV;
        double tarVelocityLeft = LV;

        if(gamepad1.bWasPressed()){
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        if(gamepad1.dpadRightWasPressed()){
            RV += stepSizes[stepIndex];
        }
        if(gamepad1.dpadLeftWasPressed()){
            RV -= stepSizes[stepIndex];
        }

        if(gamepad1.dpadUpWasPressed()){
            LV += stepSizes[stepIndex];
        }
        if(gamepad1.dpadDownWasPressed()){
            LV -= stepSizes[stepIndex];
        }


        leftOuttake.setVelocity(tarVelocityRight);
        rightOuttake.setVelocity(tarVelocityLeft);

        double RightVelocity = rightOuttake.getVelocity();
        double LeftVelocity = leftOuttake.getVelocity();
        double errorShooterRight = tarVelocityRight - RightVelocity;
        double errorShooterLeft = tarVelocityLeft - LeftVelocity;

        telemetry.addData("Left Outtake", leftOuttake.getVelocity());
        telemetry.addData("Right Outtake", rightOuttake.getVelocity());


        telemetry.addData( "Target Velocity Right", tarVelocityRight);
        telemetry.addData( "Target Velocity Left", tarVelocityLeft);
        telemetry.addData( "Current Velocity Left Wheel", "%.2f", LeftVelocity);
        telemetry.addData( "Current Velocity RIght Wheel", "%.2f", RightVelocity);
        telemetry.addData( "Error Left",  "%.2f", errorShooterLeft);
        telemetry.addData( "Error Right",  "%.2f", errorShooterRight);
        telemetry.addLine("------------------------------------");
        telemetry.addData( "Tuning Right Velocity",  "%.4f (D-Pad U/D)", RV);
        telemetry.addData( "Tuning Left Velocity", "%.4f (D-Pad L/R)", LV);
        telemetry.addData( "Step Size",  "%.4f (B Button)", stepSizes[stepIndex]);
    }
}