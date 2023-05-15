package org.iq47.message;

import org.springframework.stereotype.Component;

@Component
public class JMSMessageConverter implements MessageConverter {

    @Override
    public String convertTicketReportMessage(TicketReportMessage ticketReportMessage) {
        return String.format("{'type':'ticket_report', 'airline_name':'%s'}", ticketReportMessage.getAirline_name());
    }
}
