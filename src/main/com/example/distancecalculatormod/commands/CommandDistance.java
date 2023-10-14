package com.example.distancecalculatormod.commands;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.BlockPos;

public class CommandDistance implements ICommand {

    // Constants for chat message prefixes and required argument count
    private static final String PREFIX = "§1§l[Distance]§r: ";
    private static final String ERROR_PREFIX = "§c§l[Error]§r: ";
    private static final String EXAMPLE_PREFIX = "§d§l[Example]§r: ";

    private static final int REQUIRED_ARGUMENT_COUNT = 7;

    @Override
    public String getCommandName() {
        return "distance";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Usage: /distance <x1> <y1> <z1> <x2> <y2> <z2> <euclidean/manhattan>";
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> aliases = new ArrayList<>();
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length != REQUIRED_ARGUMENT_COUNT) {
            sender.addChatMessage(new ChatComponentText(ERROR_PREFIX + "Usage: <x1> <y1> <z1> <x2> <y2> <z2> <euclidean/manhattan>\n" + EXAMPLE_PREFIX + " /distance 392 -43 81 48 293 58 euclidean\n" + EXAMPLE_PREFIX + " /distance 392 -43 81 48 293 58 manhattan"));
            return;
        }

        try {
            double x1 = Double.parseDouble(args[0]);
            double y1 = Double.parseDouble(args[1]);
            double z1 = Double.parseDouble(args[2]);
            double x2 = Double.parseDouble(args[3]);
            double y2 = Double.parseDouble(args[4]);
            double z2 = Double.parseDouble(args[5]);
            String method = args[6];

            if (isValidCoordinate(x1, y1, z1) && isValidCoordinate(x2, y2, z2)) {
                double distance = 0.0;

                if (method.equalsIgnoreCase("euclidean")) {
                    distance = calculateEuclideanDistance(x1, y1, z1, x2, y2, z2);
                } else if (method.equalsIgnoreCase("manhattan")) {
                    distance = calculateManhattanDistance(x1, y1, z1, x2, y2, z2);
                } else {
                    sender.addChatMessage(new ChatComponentText(ERROR_PREFIX + "Invalid distance method. Use 'euclidean' or 'manhattan'."));
                    return;
                }

                sender.addChatMessage(new ChatComponentText(PREFIX + "The " + method + " distance between (" + x1 + ", " + y1 + ", " + z1 + ") and (" + x2 + ", " + y2 + ", " + z2 + ") is ~" + String.format("%.2f", distance) + " blocks."));
            } else {
                sender.addChatMessage(new ChatComponentText(PREFIX + "Invalid coordinates. Coordinates must be within the allowed Minecraft ranges."));
            }
        } catch (NumberFormatException e) {
            sender.addChatMessage(new ChatComponentText(ERROR_PREFIX + "Invalid input. Please provide valid numbers for coordinates."));
        }
    }

    private boolean isValidCoordinate(double x, double y, double z) {
        return x >= -30_000_000 && x <= 30_000_000 &&
               y >= -319 && y <= 319 &&
               z >= -30_000_000 && z <= 30_000_000;
    }

    private double calculateEuclideanDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    private double calculateManhattanDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1) + Math.abs(z2 - z1);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}