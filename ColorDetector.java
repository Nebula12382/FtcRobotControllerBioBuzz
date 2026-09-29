package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorDetector {

    public enum DetectedColor { RED, BLUE, NONE }

    private NormalizedColorSensor colorSensor;
    private DistanceSensor distanceSensor;

    private static final float GAIN = 2.0f;
    private static final double BALL_PRESENT_CM = 3.0;

    // Red hue wraps around 0/360, so it's "below RED_HUE_LOW or above RED_HUE_HIGH"
    private static final float RED_HUE_LOW = 30;
    private static final float RED_HUE_HIGH = 330;
    private static final float BLUE_HUE_MIN = 190;
    private static final float BLUE_HUE_MAX = 250;

    private final float[] hsv = new float[3];
    private float hue;
    private double distanceCm;
    private DetectedColor color = DetectedColor.NONE;

    public void HardwareMapSubsystem(HardwareMap hardwareMap) {
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
        colorSensor.setGain(GAIN);
        distanceSensor = (DistanceSensor) colorSensor;
    }

    // Reads the sensor once; call this once per loop
    public DetectedColor readColor() {
        distanceCm = distanceSensor.getDistance(DistanceUnit.CM);

        NormalizedRGBA rgba = colorSensor.getNormalizedColors();
        Color.colorToHSV(rgba.toColor(), hsv);
        hue = hsv[0];

        if (distanceCm > BALL_PRESENT_CM) {
            color = DetectedColor.NONE;
        } else if (hue < RED_HUE_LOW || hue > RED_HUE_HIGH) {
            color = DetectedColor.RED;
        } else if (hue > BLUE_HUE_MIN && hue < BLUE_HUE_MAX) {
            color = DetectedColor.BLUE;
        } else {
            color = DetectedColor.NONE;
        }
        return color;
    }

    public void update_telemetry(Telemetry telemetry) {
        telemetry.addData("Ball Color", color);
        telemetry.addData("Hue", hue);
        telemetry.addData("Distance (cm)", distanceCm);
    }
}
