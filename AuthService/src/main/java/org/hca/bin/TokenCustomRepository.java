package org.hca.bin;

import java.time.LocalDateTime;

public interface TokenCustomRepository {
    long updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
