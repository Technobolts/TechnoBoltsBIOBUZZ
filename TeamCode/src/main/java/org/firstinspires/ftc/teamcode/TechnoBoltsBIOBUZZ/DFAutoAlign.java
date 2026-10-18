package org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware. HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class DFAutoAlign {

    private final double TICKS_PER_REV = 0.8;

    public Servo turret;

    public GoBildaPinpointDriver pinpoint;
//Initialize Stuff 11

    private void initTurret (HardwareMap hwMap) {

        turret = hwMap.get(Servo.class, "turret");
        turret.setPosition(0.5);
    }

    private void initPinpoint (HardwareMap hwMap, Pose2D startingPose) {
        pinpoint = hwMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets (-3.6, -6.46, DistanceUnit.INCH);
        pinpoint.setEncoderResolution (GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections (GoBildaPinpointDriver. EncoderDirection.FORWARD, GoBildaPinpointDriver. EncoderDirection.FORWARD);
        pinpoint.recalibrateIMU();
        pinpoint.setPosition(startingPose);
        }

        public void initialize (HardwareMap hwMap, Pose2D startingPose) {
            initTurret(hwMap);
            initPinpoint(hwMap, startingPose);
        }
        public void reset (Pose2D resetPose) {
            pinpoint.recalibrateIMU();
            pinpoint.setPosition(resetPose);
        }
//Get Angles 11

        public double getRobotHeading() { return pinpoint.getHeading (AngleUnit.DEGREES); }

        public double getTurretAngle() { return (turret.getPosition()/TICKS_PER_REV)*360;}

        public double getTheta() {
            pinpoint.update();
            double absForwardPodValue = Math.abs(103 - pinpoint.getPosition().getX(DistanceUnit.INCH));
            double absStrafePodValue = Math.abs(59 - pinpoint.getPosition().getY(DistanceUnit.INCH));
            return Math.toDegrees(Math.atan(absForwardPodValue / absStrafePodValue));
        }

    public void stop() { turret.setPosition(turret.getPosition()); }

    public double autoAlign(boolean isBlue) {
        if (!isBlue) {

            turret.setPosition(0.5 + ((((-(90 - getTheta())) + getRobotHeading()) / 360) * TICKS_PER_REV));
            return 0.5 + ((((-(90 - getTheta())) + getRobotHeading()) / 360) * TICKS_PER_REV);
        }
else {
            turret.setPosition(0.5+(((((90-getTheta()))-getRobotHeading())/360) * TICKS_PER_REV));
            return 0.5+(((((90-getTheta()))-getRobotHeading())/360)*TICKS_PER_REV);
        }
    }
}