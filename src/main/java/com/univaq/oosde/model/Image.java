package com.univaq.oosde.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Image {

    private int id, artwork_id;
    private String img_url, transcription;
    private boolean img_validated, tr_validated;

    //Costruttore vuoto
    public Image() {
    }

    //Costruttore che prevede tutti i campi
    public Image(int id, int artwork_id, String img_url, String transcription, boolean img_validated, boolean tr_validated) {
        this.id = id;
        this.img_url = img_url;
        this.artwork_id = artwork_id;
        this.transcription = transcription;
        this.img_validated = img_validated;
        this.tr_validated = tr_validated;
    }

    //costruttore da oggetto ResultSet (query DB)
    public Image(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("img_id"));
        this.setImg_url(resultSet.getString("image_url"));
        this.setArtwork_id(resultSet.getInt("artwork_id"));
        this.setTranscription(resultSet.getString("transcription"));
        this.setImg_validated(resultSet.getBoolean("img_validated"));
        this.setTr_validated(resultSet.getBoolean("tr_validated"));
    }


    //Getters and Setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getArtwork_id() {
        return artwork_id;
    }

    public void setArtwork_id(int artwork_id) {
        this.artwork_id = artwork_id;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public boolean isImg_validated() {
        return img_validated;
    }

    public void setImg_validated(boolean img_validated) {
        this.img_validated = img_validated;
    }

    public boolean isTr_validated() {
        return tr_validated;
    }

    public void setTr_validated(boolean tr_validated) {
        this.tr_validated = tr_validated;
    }

    public List<Image> getPagesByArtworkId(int artId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM image WHERE artwork_id =" + artId + " AND img_validated = 1";
        ResultSet resultSet = statement.executeQuery(sql);
        List<Image> pages = new LinkedList<>();
        while (resultSet.next()) {
            Image img = new Image(resultSet);
            pages.add(img);
        }
        return pages;
    }

    public static Image getImageById(int imgId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM image WHERE img_id =" + imgId;
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            Image img = new Image(resultSet);
            return img;
        }
        return null;
    }

    public static boolean insertTrascrizione(int imgId, String transcription) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pstmt = connection.prepareStatement("UPDATE image SET transcription = (?) WHERE img_id = " + imgId);
        pstmt.setString(1, transcription); // this is your html string from step #1
        int res = pstmt.executeUpdate();
        return true;
    }

    public static String getTranscriptionByImgId(int idImg) throws SQLException {
        Image img = Image.getImageById(idImg);
        return img.getTranscription();
    }

    public static List<Image> getNotValidatedImages(int artId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM image WHERE img_validated = 0 AND image.artwork_id = " + artId;
        ResultSet resultSet = statement.executeQuery(sql);
        List<Image> pages = new LinkedList<>();
        while (resultSet.next()) {
            Image img = new Image(resultSet);
            pages.add(img);
        }
        return pages;
    }

    public static List<Image> getTranscriptionsToValidate() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM image WHERE tr_validated = 0";
        ResultSet resultSet = statement.executeQuery(sql);
        List<Image> unvalidatedTranscriptionList = new LinkedList<>();
        while (resultSet.next()) {
            Image img = new Image(resultSet);
            unvalidatedTranscriptionList.add(img);
        }
        return unvalidatedTranscriptionList;
    }

    public static void validateTranscription(int imgId, String transcription) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pstmt = connection.prepareStatement("UPDATE image SET transcription = ?, tr_validated = ? WHERE img_id =  ? ");
        pstmt.setString(1, transcription);
        pstmt.setInt(2, 1);
        pstmt.setInt(3, imgId);
        pstmt.executeUpdate();
        String sql = "DELETE FROM write_assignment WHERE image_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, imgId);
        preparedStatement.executeUpdate();
    }

    public static List<Image> getImagesToValidate() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM image WHERE img_validated = 0";
        ResultSet resultSet = statement.executeQuery(sql);
        List<Image> pages = new LinkedList<>();
        while (resultSet.next()) {
            Image img = new Image(resultSet);
            pages.add(img);
        }
        return pages;
    }

    public static long getImageSize(int artId, String imgUrl) {
        FileInfo file = new FileInfo(new File("C:/Users/simon/Documents/oosde/src/main/resources/static/" + artId + File.separator + imgUrl));
        return file.getSize();
    }

    public static int getImageWidth(int artId, String imgUrl) throws IOException {
        BufferedImage bimg = ImageIO.read(new File("C:/Users/simon/Documents/oosde/src/main/resources/static/" + artId + File.separator + imgUrl));
        return bimg.getWidth();
    }

    public static int getImageHeight(int artId, String imgUrl) throws IOException {
        BufferedImage bimg = ImageIO.read(new File("C:/Users/simon/Documents/oosde/src/main/resources/static/" + artId + File.separator + imgUrl));
        return bimg.getHeight();
    }

    static class FileInfo {


        public enum Timefield {
            CREATED, ACCESSED, WRITTEN;

        }
        private final DateFormat FORMATTER = new SimpleDateFormat(
                "dd/MM/yyyy  hh:mm");

        private File file;

        private boolean hasLoaded = false;
        private String owner;
        private Map<Timefield, java.util.Date> timefields = new HashMap<Timefield, java.util.Date>();
        public FileInfo(File file) {
            this.file = file;
        }

        private String getTimefieldSwitch(Timefield field) {
            switch (field) {
                case CREATED:
                    return "C";
                case ACCESSED:
                    return "A";
                default:
                    return "W";
            }
        }

        private void shellToDir(Timefield timefield) throws IOException,
                ParseException {
            Runtime systemShell = Runtime.getRuntime();
            Process output = systemShell.exec(String.format("cmd /c dir /Q /R /T%s %s ", getTimefieldSwitch(timefield), file.getAbsolutePath()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(output.getInputStream()));
            String outputLine = null;
            while ((outputLine = reader.readLine()) != null) {
                if (outputLine.contains(file.getName())) {
                    timefields.put(timefield, FORMATTER.parse(outputLine.substring(0, 17)));
                    owner = outputLine.substring(36, 59);
                }
            }
        }

        private void load() throws IOException, ParseException {
            if (hasLoaded)
                return;
            shellToDir(Timefield.CREATED);
            shellToDir(Timefield.ACCESSED);
            shellToDir(Timefield.WRITTEN);
        }

        public String getName() {
            return file.getName();
        }

        public String getAbsolutePath() {
            return file.getAbsolutePath();
        }

        public long getSize() {
            return file.length();
        }

        public Date getLastModified() {
            return new Date(file.lastModified());
        }

        public String getOwner() throws IOException, ParseException {
            load();
            return owner;
        }

        public java.util.Date getCreated() throws IOException, ParseException {
            load();
            return timefields.get(Timefield.CREATED);
        }

        public java.util.Date getAccessed() throws IOException, ParseException {
            load();
            return timefields.get(Timefield.ACCESSED);
        }

        public java.util.Date getWritten() throws IOException, ParseException {
            load();
            return timefields.get(Timefield.WRITTEN);
        }


    }
    public static void validateImage(int imgId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pstmt = connection.prepareStatement("UPDATE image SET img_validated = ? WHERE img_id =  ? ");
        pstmt.setInt(1, 1);
        pstmt.setInt(2, imgId);
        pstmt.executeUpdate();
    }

    public static void insertImage(int path, String fileName) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        String sql = "INSERT INTO image(image_url, artwork_id) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, fileName);
        statement.setInt(2, path);
        statement.executeUpdate();
    }
}