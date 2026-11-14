
package org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class DFAutoAlign {

    // ==========================================
    // CONFIGURATION
    // ==========================================

    private static final double FIELD_SIZE = 144.0;
    private static final double MIDFIELD_X = 72.0;

    private static final double START_X = 144.0;
    private static final double START_Y = 48.0;
    private static final double START_HEADING = 0.0;

    // Gear ratio: 240 / 100
    private static final double GEAR_RATIO = 2.4;

    // Assumes a standard 180-degree positional servo
    private static final double SERVO_TRAVEL_DEGREES = 180.0;

    private static final double SERVO_CENTER = 0.5;

    // Change to -1 if the turret turns in the wrong direction
    private static final double TURRET_DIRECTION = 1.0;

    // Mechanical calibration offset
    private static final double TURRET_ZERO_OFFSET = 0.0;

    // Hysteresis prevents switching due to small position fluctuations
    private static final double MIDFIELD_HYSTERESIS = 2.0;

    // ==========================================
    // GOAL COORDINATES (INCHES)
    // ==========================================

    // Approximate positions based on your field diagram
    private static final double RED_RIGHT_X = 144.0;
    private static final double RED_RIGHT_Y = 96.0;

    private static final double RED_BOTTOM_X = 96.0;
    private static final double RED_BOTTOM_Y = 0.0;

    private static final double BLUE_LEFT_X = 0.0;
    private static final double BLUE_LEFT_Y = 48.0;

    private static final double BLUE_TOP_X = 48.0;
    private static final double BLUE_TOP_Y = 144.0;

    // ==========================================
    // HARDWARE
    // ==========================================

    public Servo turret;
    public GoBildaPinpointDriver pinpoint;

    private double robotX;
    private double robotY;
    private double robotHeading;

    private boolean crossedMidfield = false;
    private boolean isBlue = false;

    private double targetX;
    private double targetY;
    private double targetServoPosition;
    private double targetTurretAngle;

    // ==========================================
    // INITIALIZATION
    // ==========================================

    public void initialize(
            HardwareMap hwMap,
            Pose2D startingPose
    ) {

        turret = hwMap.get(Servo.class, "turret");

        pinpoint = hwMap.get(
                GoBildaPinpointDriver.class,
                "pinpoint"
        );

        turret.setPosition(SERVO_CENTER);

        pinpoint.setOffsets(
                -3.6,
                -6.46,
                DistanceUnit.INCH
        );

        pinpoint.setEncoderResolution(
                GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD
        );

        pinpoint.setEncoderDirections(
                GoBildaPinpointDriver.EncoderDirection.REVERSED,
                GoBildaPinpointDriver.EncoderDirection.FORWARD
        );

        pinpoint.recalibrateIMU();

        pinpoint.setPosition(startingPose);

        robotX = START_X;
        robotY = START_Y;
        robotHeading = START_HEADING;

        crossedMidfield = false;

        updateGoal();
    }

    // ==========================================
    // RESET
    // ==========================================

    public void reset(Pose2D resetPose) {

        pinpoint.recalibrateIMU();
        pinpoint.setPosition(resetPose);

        crossedMidfield = false;

        turret.setPosition(SERVO_CENTER);
    }

    // ==========================================
    // ALLIANCE
    // ==========================================

    public void setAlliance(boolean blue) {

        isBlue = blue;

        updateGoal();
    }

    public boolean isBlueAlliance() {
        return isBlue;
    }

    // ==========================================
    // PINPOINT LOCALIZATION
    // ==========================================

    public void updateLocalization() {

        pinpoint.update();

        Pose2D position = pinpoint.getPosition();

        robotX = position.getX(DistanceUnit.INCH);
        robotY = position.getY(DistanceUnit.INCH);

        robotHeading = pinpoint.getHeading(
                AngleUnit.DEGREES
        );

        updateGoal();
    }

    // ==========================================
    // MIDFIELD DETECTION
    // ==========================================

    private void updateGoal() {

        if (!crossedMidfield &&
                robotX < MIDFIELD_X - MIDFIELD_HYSTERESIS) {

            crossedMidfield = true;
        }

        else if (crossedMidfield &&
                robotX > MIDFIELD_X + MIDFIELD_HYSTERESIS) {

            crossedMidfield = false;
        }

        if (!isBlue) {

            if (!crossedMidfield) {

                targetX = RED_RIGHT_X;
                targetY = RED_RIGHT_Y;

            } else {

                targetX = RED_BOTTOM_X;
                targetY = RED_BOTTOM_Y;
            }

        } else {

            if (!crossedMidfield) {

                targetX = BLUE_TOP_X;
                targetY = BLUE_TOP_Y;

            } else {

                targetX = BLUE_LEFT_X;
                targetY = BLUE_LEFT_Y;
            }
        }
    }

    // ==========================================
    // ANGLE CALCULATION
    // ==========================================

    private double normalizeAngle(double angle) {

        while (angle > 180) {
            angle -= 360;
        }

        while (angle < -180) {
            angle += 360;
        }

        return angle;
    }

    public double getTargetAngle() {

        double deltaX = targetX - robotX;
        double deltaY = targetY - robotY;

        double absoluteAngle = Math.toDegrees(
                Math.atan2(deltaY, deltaX)
        );

        double relativeAngle = normalizeAngle(
                absoluteAngle - robotHeading
        );

        return relativeAngle;
    }

    // ==========================================
    // GEAR RATIO CONVERSION
    // ==========================================

    public double calculateServoPosition() {

        targetTurretAngle =
                getTargetAngle() + TURRET_ZERO_OFFSET;

        double servoRotation =
                (targetTurretAngle * GEAR_RATIO)
                        * TURRET_DIRECTION;

        double position =
                SERVO_CENTER +
                        (servoRotation / SERVO_TRAVEL_DEGREES);

        return clamp(position, 0.0, 1.0);
    }

    // ==========================================
    // AUTOMATIC ALIGNMENT
    // ==========================================

    public double autoAlign() {

        targetServoPosition = calculateServoPosition();

        turret.setPosition(targetServoPosition);

        return targetServoPosition;
    }

    // ==========================================
    // SERVO CONTROLS
    // ==========================================

    public void centerTurret() {

        turret.setPosition(SERVO_CENTER);
    }

    public double getServoPosition() {

        return turret.getPosition();
    }

    public double getTurretAngle() {

        return ((turret.getPosition() - SERVO_CENTER)
                * SERVO_TRAVEL_DEGREES)
                / GEAR_RATIO;
    }

    public void stop() {

        if (turret != null) {
            turret.setPosition(turret.getPosition());
        }
    }

    // ==========================================
    // TELEMETRY GETTERS
    // ==========================================

    public double getX() {
        return robotX;
    }

    public double getY() {
        return robotY;
    }

    public double getHeading() {
        return robotHeading;
    }

    public double getGoalX() {
        return targetX;
    }

    public double getGoalY() {
        return targetY;
    }

    public boolean hasCrossedMidfield() {
        return crossedMidfield;
    }

    public double getCalculatedTargetAngle() {
        return targetTurretAngle;
    }

    public double getTargetServoPosition() {
        return targetServoPosition;
    }

    private double clamp(
            double value,
            double minimum,
            double maximum
    ) {

        return Math.max(
                minimum,
                Math.min(maximum, value)
        );
    }
}
