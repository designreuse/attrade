package by.attrade.domain;

public final class Views {
    public interface Message extends Id, Text, Tag, CreationLDT{};
    public interface IdText extends Id, Text{};
    public interface Id{};
    public interface Text{};
    public interface Tag{};
    public interface CreationLDT{};
}
