package hu.evocelot.sample.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interceptor for logging HTTP request and response details.
 * <p>
 * This class intercepts incoming HTTP requests and outgoing responses to log
 * relevant details such as request method, URL, headers, parameters, body,
 * response status, and response headers. It also generates a unique request ID
 * for each request to track the flow through the system.
 * </p>
 * 
 * @author mark.danisovszky
 */
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LogManager.getLogger(LoggingInterceptor.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String REQUEST_ID_HEADER_KEY = "REQUEST-ID";

    /**
     * Intercepts the request before it reaches the handler.
     * <p>
     * This method generates or retrieves a unique request ID, collects the details
     * of the incoming request, logs the details, and adds the request ID to the
     * response headers.
     * </p>
     * 
     * @param request  - the incoming HTTP request.
     * @param response - the outgoing HTTP response.
     * @param handler  - the handler that will process the request.
     * @return - {@code true} to allow the request to continue; {@code false} to
     *         block the request.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Wrap the request to enable caching for reading the request body.
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }

        // Retrieve or generate a unique request ID.
        String requestId = (String) request.getAttribute(REQUEST_ID_HEADER_KEY);
        if (Objects.isNull(requestId)) {
            requestId = UUID.randomUUID().toString();
            request.setAttribute(REQUEST_ID_HEADER_KEY, requestId);
        }

        // Collect and log request details.
        Map<String, Object> requestDetails = collectRequestDetails((ContentCachingRequestWrapper) request);
        requestDetails.put(REQUEST_ID_HEADER_KEY, requestId);
        try {
            String jsonLog = OBJECT_MAPPER.writeValueAsString(requestDetails);
            LOG.info("Request accepted: {}", jsonLog);
        } catch (JsonProcessingException e) {
            LOG.error("Error serializing request details to JSON:", e);
        }

        // Add the request ID to the response headers.
        response.addHeader(REQUEST_ID_HEADER_KEY, requestId);

        return true;
    }

    /**
     * Handles logging of the response details after the request has been processed.
     * <p>
     * This method logs the response status, headers, and any exception that may
     * have occurred during the processing of the request.
     * </p>
     * 
     * @param request  - the incoming HTTP request.
     * @param response - the outgoing HTTP response.
     * @param handler  - the handler that processed the request.
     * @param ex       - any exception thrown during the request processing (can be
     *                 {@code null}).
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        // Collect and log response details.
        Map<String, Object> responseDetails = collectResponseDetails(response);
        if (ex != null) {
            responseDetails.put("exception", ex.getMessage());
        }

        try {
            String jsonLog = OBJECT_MAPPER.writeValueAsString(responseDetails);
            LOG.info("Response created: {}", jsonLog);
        } catch (JsonProcessingException e) {
            LOG.error("Error serializing response details to JSON:", e);
        }
    }

    /**
     * Collects the details of the HTTP request.
     * <p>
     * This method extracts the request method, URL, query parameters, headers, and
     * body from the request for logging purposes.
     * </p>
     * 
     * @param request - the wrapped HTTP request containing the details to collect.
     * @return - a {@link Map} containing the collected request details.
     */
    private Map<String, Object> collectRequestDetails(ContentCachingRequestWrapper request) {
        Map<String, Object> details = new HashMap<>();
        details.put("method", request.getMethod());
        details.put("url", request.getRequestURL().toString());
        details.put("query", request.getQueryString());

        // Collect request headers.
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        details.put("headers", headers);

        // Collect request parameters.
        Map<String, String[]> parameters = request.getParameterMap();
        details.put("parameters", parameters);

        // Collect request body, if available.
        try {
            String body = new String(request.getContentAsByteArray(), request.getCharacterEncoding());
            details.put("body", body);
        } catch (Exception e) {
            details.put("bodyError", "Error reading body: " + e.getMessage());
        }

        return details;
    }

    /**
     * Collects the details of the HTTP response.
     * <p>
     * This method extracts the response status and headers for logging purposes.
     * </p>
     * 
     * @param response - the HTTP response containing the details to collect.
     * @return - a {@link Map} containing the collected response details.
     */
    private Map<String, Object> collectResponseDetails(HttpServletResponse response) {
        Map<String, Object> details = new HashMap<>();
        details.put("status", response.getStatus());

        // Collect response headers.
        Map<String, String> headers = new HashMap<>();
        for (String headerName : response.getHeaderNames()) {
            headers.put(headerName, response.getHeader(headerName));
        }
        details.put("headers", headers);

        return details;
    }
}
