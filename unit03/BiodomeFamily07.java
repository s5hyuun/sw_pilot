package unit03;

import java.util.*;

// 예외 정의
class MusicException extends Exception {
    public MusicException(String message) {
        super(message);
    }
}

// Song 클래스
abstract class Song {
    private String title;
    private int duration;
    private String artist;

    public Song(String title, int duration, String artist) {
        this.title = title;
        this.duration = duration;
        this.artist = artist;
    }

    public String getTitle() { return title; }
    public int getDuration() { return duration; }
    public String getArtist() { return artist; }

    public abstract String getInfo();
}

class AnimalSong extends Song {
    private String targetAnimal;

    public AnimalSong(String title, int duration, String artist, String targetAnimal) {
        super(title, duration, artist);
        this.targetAnimal = targetAnimal;
    }

    public String getTargetAnimal() { return targetAnimal; }

    @Override
    public String getInfo() {
        return getTitle() + ", " + getDuration() + "분, " + getArtist() + ", " + targetAnimal;
    }
}

class ManagerSong extends Song {
    private String genre;

    public ManagerSong(String title, int duration, String artist, String genre) {
        super(title, duration, artist);
        this.genre = genre;
    }

    public String getGenre() { return genre; }

    @Override
    public String getInfo() {
        return getTitle() + ", " + getDuration() + "분, " + getArtist() + ", " + genre;
    }
}

class Player {
    private Song currentSong;
    private int volume = 1;

    public void play(Song song) {
        if (song instanceof AnimalSong && volume > 5) {
            volume = 5;
            System.out.println("볼륨을 5로 설정합니다.");
        }
        this.currentSong = song;
        System.out.println(song.getInfo() + " 재생합니다.");
    }

    public void pause() {
        System.out.println("재생을 일시 정지합니다.");
    }

    public void setVolume(int volume) throws MusicException {
        if (currentSong instanceof AnimalSong) {
            if (volume < 0 || volume > 5) throw new MusicException("동물 노래의 볼륨 범위는 0~5입니다.");
        } else if (currentSong instanceof ManagerSong) {
            if (volume < 0 || volume > 50) throw new MusicException("사람 노래의 볼륨 범위는 0~50입니다.");
        }
        this.volume = volume;
        System.out.println("볼륨을 " + volume + "으로 설정합니다.");
    }
}

class MusicLibrary {
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) throws MusicException {
        for (Song s : songs) {
            if (s.getTitle().equals(song.getTitle())) {
                throw new MusicException("같은 제목의 노래가 이미 존재합니다.");
            }
        }
        songs.add(song);
        System.out.println("새로운 노래 \"" + song.getInfo() + "\" 추가되었습니다.");
    }

    public void removeSong(String title) throws MusicException {
        Iterator<Song> iterator = songs.iterator();
        while (iterator.hasNext()) {
            Song s = iterator.next();
            if (s.getTitle().equals(title)) {
                System.out.println("노래 \"" + s.getInfo() + "\" 삭제되었습니다.");
                iterator.remove();
                return;
            }
        }
        throw new MusicException("해당 제목의 노래를 찾을 수 없습니다.");
    }

    public List<Song> searchAnimalSongs() {
        List<Song> result = new ArrayList<>();
        for (Song s : songs) {
            if (s instanceof AnimalSong) result.add(s);
        }
        return result;
    }

    public List<Song> searchManagerSongs() {
        List<Song> result = new ArrayList<>();
        for (Song s : songs) {
            if (s instanceof ManagerSong) result.add(s);
        }
        return result;
    }

    public List<Song> searchByTitle(String title) {
        List<Song> result = new ArrayList<>();
        for (Song s : songs) {
            if (s.getTitle().contains(title)) result.add(s);
        }
        return result;
    }

    public List<Song> searchByAnimal(String animal) {
        List<Song> result = new ArrayList<>();
        for (Song s : songs) {
            if (s instanceof AnimalSong && ((AnimalSong) s).getTargetAnimal().equals(animal)) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Song> searchByGenre(String genre) {
        List<Song> result = new ArrayList<>();
        for (Song s : songs) {
            if (s instanceof ManagerSong && ((ManagerSong) s).getGenre().equals(genre)) {
                result.add(s);
            }
        }
        return result;
    }
}

public class BiodomeFamily07 {
    public static void main(String[] args) {
        try {
            MusicLibrary lib = new MusicLibrary();
            Player player = new Player();

            lib.addSong(new AnimalSong("초원을 그리며", 2, "레이나", "사슴"));
            lib.addSong(new AnimalSong("영웅 호테", 1, "돈키", "당나귀"));
            lib.addSong(new AnimalSong("과자를 줄게", 3, "제롬", "코끼리"));
            lib.addSong(new ManagerSong("화양연화", 2, "장양림", "재즈"));
            lib.addSong(new ManagerSong("시간의 수평선", 4, "하윤", "팝"));

            List<Song> managerSongs = lib.searchManagerSongs();
            if (!managerSongs.isEmpty()) {
                player.play(managerSongs.get(0));
                player.setVolume(30);
            }

            List<Song> donkeySongs = lib.searchByAnimal("당나귀");
            if (!donkeySongs.isEmpty()) {
                player.play(donkeySongs.get(0));
            }

            lib.removeSong("시간의 수평선");
        } catch (MusicException e) {
            System.out.println("오류: " + e.getMessage());
        }
    }
}
