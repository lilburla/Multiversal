package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.PedroPathing;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PedroConfig {

   private final Map<String, String> values;

    public PedroConfig(Map<String, String> values) {
        this.values = values;
    }

    public static PedroConfig parse(InputStream in) throws IOException {
        Map<String, String> values = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            int colon = trimmed.indexOf(':');
            if (colon < 0) {
                continue;
            }
            String key = trimmed.substring(0, colon).trim();
            String value = trimmed.substring(colon + 1).trim();
            if (!value.startsWith("\"") && value.contains("#")) {
                value = value.substring(0, value.indexOf('#')).trim();
            }
            values.put(key, value.replace("\"", ""));
        }
        return new PedroConfig(values);
    }

    public String getString(String key, String fallback) {
        String value = values.get(key);
        return value == null || value.isEmpty() ? fallback : value;
    }

    public double getDouble(String key, double fallback) {
        String value = values.get(key);
        if (value == null || value.isEmpty()) {
            return fallback;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    public DcMotorSimple.Direction getMotorDirection(String key, DcMotorSimple.Direction fallback) {
        String value = getString(key, "");
        if (value.equalsIgnoreCase("REVERSE") || value.equalsIgnoreCase("REVERSED")) {
            return DcMotorSimple.Direction.REVERSE;
        }
        if (value.equalsIgnoreCase("FORWARD")) {
            return DcMotorSimple.Direction.FORWARD;
        }
        return fallback;
    }

    public GoBildaPinpointDriver.EncoderDirection getPodDirection(String key, GoBildaPinpointDriver.EncoderDirection fallback) {
        String value = getString(key, "");
        if (value.equalsIgnoreCase("REVERSED") || value.equalsIgnoreCase("REVERSE")) {
            return GoBildaPinpointDriver.EncoderDirection.REVERSED;
        }
        if (value.equalsIgnoreCase("FORWARD")) {
            return GoBildaPinpointDriver.EncoderDirection.FORWARD;
        }
        return fallback;
    }

    public GoBildaPinpointDriver.GoBildaOdometryPods getPodType(String key, GoBildaPinpointDriver.GoBildaOdometryPods fallback) {
        String value = getString(key, "");
        if (value.equalsIgnoreCase("SWING_ARM") || value.equalsIgnoreCase("SWINGARM")) {
            return GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD;
        }
        if (value.equalsIgnoreCase("FOUR_BAR") || value.equalsIgnoreCase("4_BAR")) {
            return GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD;
        }
        return fallback;
    }
}
