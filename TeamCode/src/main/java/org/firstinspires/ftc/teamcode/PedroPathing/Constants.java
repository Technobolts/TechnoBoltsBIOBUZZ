package org.firstinspires.ftc.teamcode.PedroPathing;

/**
 * Home for the generated, robot-specific Pedro configuration.
 *
 * <p>Do not add a Follower factory until the BIOBUZZ drivetrain and localizer have been
 * configured and tuned with the included Pedro procedures. This avoids baking DECODE's
 * robot measurements into a new robot.</p>
 */
public final class Constants {
    private Constants() {
        // Utility class.
    }

    /*
     * TODO(BIOBUZZ-CALIBRATE): Generate the active drivetrain configuration with
     * PedroPathing.procedures.MecanumTuner. Do not use these DECODE values as active code.
     * DECODE reference values:
     *   front-left=FORWARD, back-left=FORWARD,
     *   front-right=REVERSE, back-right=REVERSE
     *   xVelocity=57.67630317267471, yVelocity=35.30537618802289
     */


    /*
     * TODO(BIOBUZZ-CALIBRATE): Choose and tune exactly one localizer before autonomous.
     * DECODE reference values (Pinpoint only; do not copy until physically verified):
     *   hardwareMapName="pinpoint"
     *   forwardPodY=-7 inches, strafePodX=3.5 inches
     *   encoderResolution=goBILDA 4-bar pod
     *   forwardEncoderDirection=REVERSED, strafeEncoderDirection=REVERSED
     */

    /*
     * TODO(BIOBUZZ-CALIBRATE): Tune follower/path values after the robot drives reliably.
     * DECODE reference values (not active):
     *   mass=10.52334, centripetalScaling=0
     *   headingPIDF=(-2, 0, 0.01, 0.025)
     *   predictiveBraking=(0.1, 0.053494634007348565, 0.0014712457201625792)
     *   pathConstraints=(0.99, 100, 1, 1)
     */
}
