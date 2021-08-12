package tw.com.housekeeping.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    private static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("index")
    public String showIndexPage() {
        DemoController.logger.debug("index: ");
        return "";
    }

    @GetMapping("/{directory}/{extension}/{createDay}")
    public List<File> listFile(@PathVariable String directory, @PathVariable String extension,
            @PathVariable String createDay) {
        DemoController.logger.debug("list directory: {} {}", directory, createDay);

        return getFiles("/" + directory.trim(), "." + extension.trim(), createDay.trim());
    }

    @DeleteMapping("/{directory}/{extension}/{createDay}")
    public void deleteFile(@PathVariable String directory, @PathVariable String extension,
            @PathVariable String createDay) {
        DemoController.logger.debug("delete files: {} {}", directory, createDay);

        getFiles("/" + directory.trim(), "." + extension.trim(), createDay.trim()).forEach(f -> {
            try {
                DemoController.logger.info("{} {}", f.getAbsolutePath(), f.delete());
            } catch (Exception e) {
                DemoController.logger.error("{}", e);
            }
        });
    }

    public List<File> getFiles(String directory, String extension, String createDay) {
        DemoController.logger.info("directory: {}, extension: {}, createDay: {}", directory, extension, createDay);

        if (!validateArgs(directory, extension, createDay))
            return null;

        try {
            char op = createDay.charAt(0);
            int index = (op == '+' || op == '-') ? 1 : 0;
            int days = Integer.parseInt(createDay.substring(index));
            DemoController.logger.info("op: {}, days: {}", op, days);

            LocalDate now = LocalDate.now();
            LocalDate startDate = now;
            LocalDate endDate = now;

            if (op == '+') {
                startDate = LocalDate.MIN;
                endDate = now.minusDays(days);
            } else if (op == '-') {
                startDate = now.minusDays(days);
                endDate = now;
            } else {
                startDate = now.minusDays(days);
                endDate = startDate;
            }

            DemoController.logger.info("start: {}, end: {}", startDate, endDate);

            final LocalDate start = startDate;
            final LocalDate end = endDate;

            return Files.list(Paths.get(directory)).filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(extension)).filter(path -> {
                        try {
                            DemoController.logger.debug("{}", path);

                            LocalDate fileDate = Files.readAttributes(path, BasicFileAttributes.class).creationTime()
                                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                            return fileDate.isEqual(start) || fileDate.isEqual(end)
                                    || (fileDate.isBefore(end) && fileDate.isAfter(start));
                        } catch (IOException e) {
                            DemoController.logger.error("{}", e);
                            return false;
                        }
                    }).map(Path::toFile).peek(f -> logger.debug("file: {}", f)).collect(Collectors.toList());
        } catch (IOException e) {
            DemoController.logger.error("{}", e);

            return null;
        }
    }

    private boolean validateArgs(String directory, String extension, String createDay) {
        if (directory == null) {
            DemoController.logger.error("invalid arguemnts. directory is null");
            return false;
        }

        if (extension == null) {
            DemoController.logger.error("invalid arguemnts. extension is null");
            return false;
        }

        if (createDay == null) {
            DemoController.logger.error("invalid arguemnts. createDay is null");
            return false;
        }

        if (!createDay.matches("^[\\+\\-]{0,1}[1-9]{1}[0-9]{0,3}")) {
            DemoController.logger.error("invalid format. range in [1, 9999]");
            return false;
        }

        return true;
    }
}
