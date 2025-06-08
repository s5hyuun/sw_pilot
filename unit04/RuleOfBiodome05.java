package unit04;

public class RuleOfBiodome05 {

    public static void main(String[] args) {
        PlantHashMap<String, String> plantMap = new PlantHashMap<>();

        // 식물 10개 추가
        plantMap.put("장미", "장미는 관상용으로 많이 재배되는 화초 중 하나이다.");
        plantMap.put("해바라기", "해바라기는 태양을 따라 움직이는 것으로 알려져 있다.");
        plantMap.put("민들레", "민들레는 약용으로도 사용되는 풀이다.");
        plantMap.put("튤립", "튤립은 봄에 피는 아름다운 꽃이다.");
        plantMap.put("선인장", "선인장은 건조한 지역에서 잘 자란다.");
        plantMap.put("연꽃", "연꽃은 물 위에 피는 꽃이다.");
        plantMap.put("코스모스", "코스모스는 가을에 피는 꽃으로 유명하다.");
        plantMap.put("벚꽃", "벚꽃은 봄철에 짧게 피는 꽃이다.");
        plantMap.put("백합", "백합은 향기가 강한 꽃으로 알려져 있다.");
        plantMap.put("라벤더", "라벤더는 진정 효과가 있는 허브이다.");

        // 식물 검색
        plantMap.get("장미");
        plantMap.get("해바라기");

        // 식물 삭제
        plantMap.remove("민들레");

        System.out.println("'장미' 인덱스: " + plantMap.getIndex("장미"));
        System.out.println("'해바라기' 인덱스: " + plantMap.getIndex("해바라기"));
    }

    static class PlantHashMap<K, V> {
        private static final int SIZE = 16;
        private Entry<K, V>[] table;

        @SuppressWarnings("unchecked")
        public PlantHashMap() {
            table = new Entry[SIZE];
        }

        public void put(K key, V value) {
            int index = getIndex(key);
            table[index] = new Entry<>(key, value);
            System.out.println("'" + key + "' 추가: '" + value + "'");
        }

        public void get(K key) {
            int index = getIndex(key);
            Entry<K, V> entry = table[index];
            if (entry != null && entry.key.equals(key)) {
                System.out.println("'" + key + "' 검색: '" + entry.value + "'");
            } else {
                System.out.println("'" + key + "'는 존재하지 않습니다.");
            }
        }

        public void remove(K key) {
            int index = getIndex(key);
            Entry<K, V> entry = table[index];
            if (entry != null && entry.key.equals(key)) {
                table[index] = null;
                System.out.println("'" + key + "' 삭제: '" + key + "'와 그 특징이 삭제되었습니다.");
            } else {
                System.out.println("'" + key + "'는 존재하지 않아 삭제할 수 없습니다.");
            }
        }

        public int getIndex(K key) {
            return Math.abs(key.hashCode()) % SIZE;
        }

        static class Entry<K, V> {
            K key;
            V value;

            Entry(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }
    }
}
