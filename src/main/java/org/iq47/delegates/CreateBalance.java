package org.iq47.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;
import org.iq47.model.entity.City;
import org.iq47.model.entity.UserBalance;
import org.iq47.service.CityService;
import org.iq47.service.UserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;

import static org.camunda.spin.Spin.JSON;

@Named("saveBalance")
@RequiredArgsConstructor
public class CreateBalance implements JavaDelegate {

    @Autowired
    private UserBalanceService balanceService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String) delegateExecution.getVariable("username");
        Integer balance = (Integer) delegateExecution.getVariable("balance");
        UserBalance userBalance = new UserBalance();
        userBalance.setUsername(username);
        userBalance.setBalance(balance);
        balanceService.save(userBalance);
    }
}
