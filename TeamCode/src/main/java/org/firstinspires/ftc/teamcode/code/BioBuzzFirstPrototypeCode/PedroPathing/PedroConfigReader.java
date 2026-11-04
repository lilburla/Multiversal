package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.PedroPathing;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.io.IOException;
import java.io.InputStream;

public class PedroConfigReader {
    public static final String CONFIG_FILE = "pedroPathingConfig.yml";

    public static PedroConfig read(HardwareMap hardwareMap) {
        try (InputStream in = hardwareMap.appContext.getAssets().open(CONFIG_FILE)) {
            return PedroConfig.parse(in);
        } catch (IOException e) {
            throw new RuntimeException("Could not read " + CONFIG_FILE, e);
        }
    }
}
