package org.iq47.message;

import org.springframework.stereotype.Component;

@Component
public class JMSMessageConverter implements MessageConverter {

    @Override
    public String convertTicketReportMessage(TicketReportMessage ticketReportMessage) {
        return String.format("{'type':'ticket_report', 'airline_name':'%d'}", ticketReportMessage.getAirline_name());
    }
}
