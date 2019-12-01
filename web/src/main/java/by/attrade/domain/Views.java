package by.attrade.domain;

public final class Views {
    public interface Message extends Id, Text, Tag, CreationLDT{}
    public interface IdText extends Id, Text{}
    public interface Id{}
    public interface Name{}
    public interface Code{}
    public interface Icon{}
    public interface Picture{}
    public interface Path{}
    public interface Price{}
    public interface Category{}
    public interface QuantityInStock{}
    public interface QuantitySupplier{}
    public interface Unit{}
    public interface Text{}
    public interface Tag{}
    public interface CreationLDT{}
    public interface IdNamePathIcon extends Id,Name,Path,Icon {}
    public interface IdNameCodePathPicturePriceCategoryQuantityInStockQuantitySupplierUnit extends Id, Name, Code, Path, Picture, Price, Category, QuantityInStock, QuantitySupplier, Unit {}
}
