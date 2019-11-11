package by.attrade.domain;

public final class Views {
    public interface Message extends Id, Text, Tag, CreationLDT{}
    public interface IdText extends Id, Text{}
    public interface Id{}
    public interface Name{}
    public interface Icon{}
    public interface Picture{}
    public interface Path{}
    public interface Price{}
    public interface Category{}
    public interface Text{}
    public interface Tag{}
    public interface CreationLDT{}
    public interface IdNamePathIcon extends Id,Name,Path,Icon {}
    public interface IdNamePathIconPicturePriceCategory extends Id, Name, Path, Icon, Picture, Price, Category {}
}
