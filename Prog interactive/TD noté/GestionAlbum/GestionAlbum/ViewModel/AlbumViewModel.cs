using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;

namespace GestionAlbum
{
    public class AlbumViewModel : Observable
    {
        private string _artist;
        public string Artist
        {
            get => _artist;
            set => SetProperty(ref _artist, value);
        }

        private string _album;
        public string Album
        {
            get => _album;
            set => SetProperty(ref _album, value);
        }

        private ObservableCollection<Track> _tracks = new ObservableCollection<Track>();
        public ObservableCollection<Track> Tracks
        {
            get => _tracks;
            set => SetProperty(ref _tracks, value); 
        }

        private Track _selectedTrack;
        public Track SelectedTrack
        {
            get => _selectedTrack;
            set => SetProperty(ref _selectedTrack, value); 
        }

        private string _toAddTrack;
        public string ToAddTrack
        {
            get => _toAddTrack;
            set => SetProperty(ref _toAddTrack, value);
        }

        public void AddTrack()
        {
            if (_toAddTrack != null && !_toAddTrack.Equals(""))
                Tracks.Add(new Track(_toAddTrack));
        }

        public void RemoveTrack()
        {
            if (_selectedTrack != null)
            {
                Tracks.Remove(SelectedTrack);
                SelectedTrack = null;
            }
        }

        //Mise à jour des numeros de pistes lors de l'ajout
        public void setNumber()
        {
            foreach (Track track in _tracks)
            {
                track.Number = _tracks.IndexOf(track) + 1;
            }
        }
    }
    
    public class Track : Observable
    {
        public Track(string TrackName)
        {
            _trackName = TrackName;
        }

        private int _number;
        public int Number
        {
            get => _number;
            set { SetProperty(ref _number, value);  OnPropertyChanged(nameof(ToPrint)); }
}

        private string _trackName;
        public string TrackName
        {
            get => _trackName;
            set { SetProperty(ref _trackName, value); OnPropertyChanged(nameof(ToPrint)); }
        }

        public string ToPrint => $"{_number} - {_trackName}";
    }
}
