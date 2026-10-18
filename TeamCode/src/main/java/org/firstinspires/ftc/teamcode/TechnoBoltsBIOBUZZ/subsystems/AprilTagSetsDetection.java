package org.firstinspires.ftc.teamcode.TechnoBoltsBIOBUZZ.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

@TeleOp(name = "AprilTag Group Scanner", group = "Testing")
public class AprilTagSetsDetection extends OpMode {

    private Limelight3A limelight;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        // Update Driver Station telemetry frequently.
        telemetry.setMsTransmissionInterval(50);

        telemetry.addLine("AprilTag Group Scanner");
        telemetry.addData("Hardware Name", limelight.getDeviceName());
        telemetry.addData("Connection Info", limelight.getConnectionInfo());
        telemetry.addLine("Ready. Press PLAY to start scanning.");
        telemetry.update();
    }

    @Override
    public void start() {
        // Pipeline 0 is already selected on the Limelight.
        // Start receiving results without repeatedly switching pipelines.
        limelight.setPollRateHz(100);
        limelight.start();
    }

    @Override
    public void loop() {
        telemetry.addLine("=== LIMELIGHT STATUS ===");
        telemetry.addData("Connected", limelight.isConnected());
        telemetry.addData("Polling Started", limelight.isRunning());

        long updateTime = limelight.getTimeSinceLastUpdate();
        telemetry.addData(
                "Time Since Last Update",
                updateTime >= 0 && updateTime < 100000
                        ? updateTime + " ms"
                        : "No recent update"
        );

        LLResult result = limelight.getLatestResult();

        if (result == null) {
            telemetry.addLine("Waiting for Limelight data...");
            telemetry.addLine("Check the Control Hub connection.");
            telemetry.update();
            return;
        }

        telemetry.addData("Valid Result", result.isValid());

        if (!result.isValid()) {
            telemetry.addLine("No valid vision result yet.");
            telemetry.addLine(
                    "Check the Limelight web dashboard and AprilTag pipeline."
            );
            telemetry.update();
            return;
        }

        List<LLResultTypes.FiducialResult> tags =
                result.getFiducialResults();

        telemetry.addData("Pipeline Index", result.getPipelineIndex());
        telemetry.addData("AprilTags Detected", tags.size());

        if (tags.isEmpty()) {
            telemetry.addLine("No AprilTags detected in this frame.");
            telemetry.update();
            return;
        }

        telemetry.addLine("=== DETECTED APRILTAG GROUPS ===");

        for (LLResultTypes.FiducialResult tag : tags) {
            int id = tag.getFiducialId();

            telemetry.addData("AprilTag ID", id);
            telemetry.addData("Group", getGroup(id));
        }

        telemetry.update();
    }

    private String getGroup(int id) {
        if (id >= 30 && id <= 33) {
            return "GROUP 1";
        } else if (id >= 34 && id <= 37) {
            return "GROUP 2";
        } else if (id >= 38 && id <= 41) {
            return "GROUP 3";
        } else if (id >= 42 && id <= 45) {
            return "GROUP 4";
        } else {
            return "UNKNOWN GROUP - CHECK ID";
        }
    }

    @Override
    public void stop() {
        if (limelight != null) {
            limelight.stop();
        }
    }
}