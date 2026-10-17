package org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp(name = "Intake Test")
public class IntakeSystem extends LinearOpMode{
    private DcMotor intake;

    @Override
    public void runOpMode() {

        intake = hardwareMap.get(DcMotor.class, "intake");

        waitForStart();

        while (opModeIsActive()) {
            intake.setPower(1.0);
        }
    }
}
