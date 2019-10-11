package by.attrade.controller.rest;

import by.attrade.controller.rest.exception.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    public List<Map<String, String>> messages = new ArrayList<>();
    private static int count = 0;

    {
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("id", "1");
        map1.put("text", "text1");
        messages.add(map1);
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("id", "2");
        map2.put("text", "text2");
        messages.add(map2);
        HashMap<String, String> map3 = new HashMap<>();
        map3.put("id", "3");
        map3.put("text", "text3");
        messages.add(map3);
    }

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages
                .stream()
                .filter(id::equals).findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(count++));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@RequestBody Map<String, String> message, @PathVariable String id) {
        Map<String, String> messageFromDB = getMessage(id);
        messageFromDB.putAll(message);
        messageFromDB.put("id", id);
        return messageFromDB;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> message = getMessage(id);
        messages.remove(message);
    }
}
