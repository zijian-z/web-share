package com.zijian.webshare.notify;

import com.zijian.webshare.exception.ResourceEmptyException;
import com.zijian.webshare.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/notify")
public class NotifyController {
    private final NotifyService notifyService;
    private final HttpSession httpSession;
    private final HashMap<String, User> map;

    @Autowired
    public NotifyController(NotifyService notifyService, HttpSession httpSession, HashMap<String, User> map) {
        this.notifyService = notifyService;
        this.httpSession = httpSession;
        this.map = map;
    }

    @PostMapping("/")
    public Page<NotifyDTO> findByConsumeUser(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        User user = map.get(httpSession.getAttribute("user").toString());
        Page<Notify> notifies = notifyService.findByConsumeUser(user, pageable);
        return notifies.map(NotifyDTO::new);
    }

    @PostMapping("/{notifyId}")
    public ResponseEntity<NotifyDTO> readNotify(@PathVariable Long notifyId) {
        Optional<Notify> optional = notifyService.findById(notifyId);
        if (optional.isPresent()) {
            return new ResponseEntity<>(new NotifyDTO(notifyService.readNotify(optional.get())), HttpStatus.OK);
        } else {
            throw new ResourceEmptyException("notify.empty");
        }
    }
}
