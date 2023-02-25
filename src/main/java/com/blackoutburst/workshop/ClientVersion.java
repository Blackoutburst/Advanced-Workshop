package com.blackoutburst.workshop;

public enum ClientVersion {

    V1_19_3(761),
    V1_19_2(760),
    V1_19_1(759),
    V1_19(759),
    V1_18_2(758),
    V1_18_1(757),
    V1_18(757),
    V1_17_1(756),
    V1_17(755),
    V1_16_5(754),
    V1_16_4(754),
    V1_16_3(753),
    V1_16_2(751),
    V1_16_1(736),
    V1_16(735),
    V1_15_2(578),
    V1_15_1(575),
    V1_15(573),
    V1_14_4(498),
    V1_14_3(490),
    V1_14_2(485),
    V1_14_1(480),
    V1_14(477),
    V1_13_2(404),
    V1_13_1(401),
    V1_13(393),
    V1_12_2(340),
    V1_12_1(338),
    V1_12(335),
    V1_11_2(316),
    V1_11_1(316),
    V1_11(315),
    V1_10_2(210),
    V1_10_1(210),
    V1_10(210),
    V1_9_4(110),
    V1_9_3(110),
    V1_9_2(109),
    V1_9_1(108),
    V1_9(107),
    V1_8_9(47),
    V1_8_8(47),
    V1_8_7(47),
    V1_8_6(47),
    V1_8_5(47),
    V1_8_4(47),
    V1_8_3(47),
    V1_8_2(47),
    V1_8_1(47),
    V1_8(47),
    V1_7_10(5),
    V1_7_9(5),
    V1_7_8(5),
    V1_7_7(5),
    V1_7_6(5),
    V1_7_5(4),
    V1_7_3(4),
    V1_7_2(4);

    private final int protocolVersion;

    ClientVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public static ClientVersion getFromProtocolVersion(int protocolVersion) {
        for (ClientVersion version : ClientVersion.values()) {
            if (version.protocolVersion == protocolVersion)
                return version;
        }
        return null;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }
}
