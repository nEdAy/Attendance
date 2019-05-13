package cn.neday.iflytek.controller;

import cn.neday.iflytek.domain.History;
import cn.neday.iflytek.service.HistoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @ApiOperation(value = "获取用户签到历史列表", notes = "获取用户签到历史列表")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<History> getUserList() {
        return historyService.findAll();
    }

    @ApiOperation(value = "获取用户签到历史信息", notes = "根据UserAccount来获取用户签到历史信息")
    @RequestMapping(value = "/{UserAccount}", method = RequestMethod.GET)
    public List<History> getHistory(@PathVariable String UserAccount) {
        return historyService.findByUserAccount(UserAccount);
    }

}
