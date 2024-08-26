package com.eonnations.eoncore.common.api.records;

import java.util.Date;
import java.util.UUID;

public record Vote(UUID uuid, Date timeStamp, String website) {
}
