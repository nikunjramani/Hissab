package com.hissab.HomePage.ui.note;

public class Note {
    String nid,noteName,noteDescription;

    public Note(){}
    public Note(String nid, String noteName, String noteDescription) {
        this.nid = nid;
        this.noteName = noteName;
        this.noteDescription = noteDescription;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
