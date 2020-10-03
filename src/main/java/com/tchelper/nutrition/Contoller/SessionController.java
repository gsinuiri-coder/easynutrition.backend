package com.tchelper.nutrition.Contoller;

import com.tchelper.nutrition.domain.model.Session;
import com.tchelper.nutrition.domain.model.Session;
import com.tchelper.nutrition.domain.repository.SessionRepository;
import com.tchelper.nutrition.domain.service.SessionService;
import com.tchelper.nutrition.exception.ResourceNotFoundException;
import com.tchelper.nutrition.resource.DietResource;
import com.tchelper.nutrition.resource.SaveSessionResource;
import com.tchelper.nutrition.resource.SessionResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name="sessions", description = "Sessions API")
@RestController
@RequestMapping("/api")
public class SessionController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/sessions")
    public Page<SessionResource> getAllSessions(Pageable pageable) {

        Page<Session> sessionsPage = sessionService.getAllSessions(pageable);
        List<SessionResource> resources = sessionsPage.getContent()
                .stream().map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/sessions/{sessionId}")
    public SessionResource getSessionById(@PathVariable (value = "sessionId") Long sessionId) {
        return convertToResource(sessionService.getSessionById(sessionId));
    }

    @PostMapping("/sessions")
    public SessionResource createSession(
            @Valid @RequestBody SaveSessionResource resource) {
        Session session = convertToEntity(resource);
        return convertToResource(sessionService.createSession(session));

    }

    @PutMapping("/sessions/{sessionId}")
    public SessionResource updateSession(@PathVariable Long sessionId,
                                   @Valid @RequestBody SaveSessionResource resource) {
        Session session = convertToEntity(resource);
        return convertToResource(
                sessionService.updateSession(sessionId, session));
    }

    @DeleteMapping("/sessions/{sessionId}")
    public ResponseEntity<?> deleteSession(@PathVariable Long sessionId) {
        return sessionService.deleteSession(sessionId);
    }

    private Session convertToEntity(SaveSessionResource resource) {
        return mapper.map(resource, Session.class);
    }

    private SessionResource convertToResource(Session entity) {
        return mapper.map(entity, SessionResource.class);
    }
}
