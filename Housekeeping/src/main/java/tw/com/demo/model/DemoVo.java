package tw.com.demo.model;

import java.io.File;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DemoVo {

    List<File> files;
}
