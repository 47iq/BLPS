package org.iq47.producer;

import org.iq47.message.TicketReportMessage;

public interface MessageSender {
    void sendTicketReportMessage(TicketReportMessage reportMessage);
}
