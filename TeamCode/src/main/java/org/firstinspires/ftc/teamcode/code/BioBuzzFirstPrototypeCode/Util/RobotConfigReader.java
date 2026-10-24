package org.firstinspires.ftc.teamcode.code.Robot.Util;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RobotConfigReader {
    public static final String CONFIG_FILE = "robotConfig.yml";

    public static RobotConfig read(HardwareMap hardwareMap) {
        Map<String, String> values = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                hardwareMap.appContext.getAssets().open(CONFIG_FILE)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int colon = line.indexOf(':');
                if (colon < 0 || line.trim().startsWith("#")) {
                    continue;
                }
                String key = line.substring(0, colon).trim();
                String value = line.substring(colon + 1).trim().replace("\"", "");
                values.put(key, value);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read " + CONFIG_FILE, e);
        }
        return new RobotConfig(values);
    }
}
