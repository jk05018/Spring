package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.springframework.core.convert.converter.Converter;

public class IpPortToStringConverter implements Converter<IpPort, String> {

  @Override
  public String convert(IpPort source) {
    return source.getIp() + ":" + source.getPort();
  }
}
