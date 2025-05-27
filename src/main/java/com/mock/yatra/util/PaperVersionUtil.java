package com.mock.yatra.util;

import java.util.List;

public class PaperVersionUtil {
	
	/**
     * Returns the next version like SSC-CGL-V01, SSC-CGL-V02, etc.
     * 
     * @param examType  The exam type (e.g., "SSC-CGL")
     * @param existingVersions List of existing versions for this exam
     * @return Next version string
     */
    public static String generateNextVersion(String examType, List<String> existingVersions) {
        int maxVersion = existingVersions.stream()
            .filter(v -> v.startsWith(examType))
            .map(v -> v.replace(examType + "-V", ""))
            .mapToInt(Integer::parseInt)
            .max()
            .orElse(0);

        return String.format("%s-V%02d", examType, maxVersion + 1);
    }
}
