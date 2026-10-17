package org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class RobotTeleOp extends OpMode {

    MecanumDrivetrain drive = new MecanumDrivetrain();

    DcMotor intake = null;

    @Override
    public void init() {
    drive.init(hardwareMap);
    intake = hardwareMap.get(DcMotor.class, "intake");

    }

    @Override
    public void loop() {
        // Left stick: forward/back (y) and strafe (x). Right stick: rotation.
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;   // stick up is negative, so invert
        double turn = gamepad1.right_stick_x;

       drive.drive(x,y,turn);

       if(gamepad1.right_bumper){
           intake.setPower(-1);
        }
       else{
           intake.setPower(0);
       }

    }
}
