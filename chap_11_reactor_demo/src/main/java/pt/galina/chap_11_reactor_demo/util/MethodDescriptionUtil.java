package pt.galina.chap_11_reactor_demo.util;

public class MethodDescriptionUtil {

    public static String getCreateFluxFromArrayDescription() {
        return "createAFluxFromArray(new String[]{\"Apple\", \"Orange\", \"Grape\", \"Banana\", \"Strawberry\"})";
    }

    public static String getCreateAFluxIntervalDescription() {
        return "createAFluxInterval(Duration.ofSeconds(1L)).take(5)";
    }

    public static String getMergeFluxesDescription() {
        return "mergeFluxes(List.of(\"Garfield\", \"Kojak\", \"Barbossa\"), \n" +
                "           List.of(\"Lasagna\", \"Lollipops\", \"Apples\"), 500L, 250L, 500L)";
    }

    public static String getZipFluxesDescription() {
        return "zipFluxes(List.of(\"Garfield\", \"Kojak\", \"Barbossa\"), \n " +
                "         List.of(\"Lasagna\", \"Lollipops\", \"Apples\"))";
    }

    public static String getFirstWithSignalDescription() {
        return "firstWithSignalFlux(List.of(\"tortoise\", \"snail\", \"sloth\"), \n " +
                "                   List.of(\"hare\", \"cheetah\", \"squirrel\"))";
    }

    public static String getSkipAFewDescription() {
        return "skipAFew(List.of(\"one\", \"two\", \"skip a few\", \"ninety nine\", \"one hundred\"), 3)";
    }

    public static String getTakeMethodDescription() {
        return "take(List.of(\"Yellowstone\", \"Yosemite\", \"Grand Canyon\", \"Zion\", \"Acadia\"), 3)";
    }

    public static String getFilterMethodDescription() {
        return "filter(List.of(\"Yellowstone\", \"Yosemite\", \"Grand Canyon\", \"Zion\", \"Acadia\"))";
    }

    public static String getDistinctMethodDescription() {
        return "distinct(List.of(\"dog\", \"cat\", \"bird\", \"dog\", \"bird\", \"anteater\"))";
    }

    public static String getMapMethodDescription() {
        return "map(List.of(\"Michael Jordan\", \"Scottie Pippen\", \"Steve Kerr\"))";
    }

    public static String getFlatMapMethodDescription() {
        return "flatMap(List.of(\"Michael Jordan\", \"Scottie Pippen\", \"Steve Kerr\"))";
    }

    public static String getBufferMethodDescription() {
        return "buffer(List.of(\"apple\", \"orange\", \"banana\", \"kiwi\", \"strawberry\"), 3)";
    }

    public static String getCollectListMethodDescription() {
        return "collectList(\"apple\", \"orange\", \"banana\", \"kiwi\", \"strawberry\")";
    }

    public static String getCollectMapDescription() {
        return "collectMap(\"aardvark\", \"elephant\", \"koala\", \"eagle\", \"kangaroo\")";
    }

    public static String getAllMethodDescription() {
        return "all(\"a\", \"aardvark\", \"elephant\", \"koala\", \"eagle\", \"kangaroo\")";
    }

    public static String getAnyMethodDescription() {
        return "any(\"w\", \"aardvark\", \"elephant\", \"koala\", \"eagle\", \"kangaroo\")";
    }
}
