package dgb.Mp.Utils;

import dgb.Mp.Picrures.Dtos.PictureDto;
import dgb.Mp.Picrures.Picture;
@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

    PictureDto toPictureDto(Picture picture);
}
