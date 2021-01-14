using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;

namespace GestionAlbum
{
    class MainViewModel : Observable
    {
        public MainViewModel()
        {
            _albumSet = new ObservableCollection<AlbumViewModel>
            {
                new AlbumViewModel() { Artist = "Bob Marley", Album = "One love",
                    Tracks = { new Track("One Love"), new Track("Rastaman vibration"), new Track("No woman no cry"),
                               new Track("Redemption Song"), new Track("Duppy Conqueror"), new Track("Commin' in from the cold") } } ,
                new AlbumViewModel() { Artist = "Nina Simone", Album = "Best of Nina Simone",
                    Tracks = { new Track("My sweet Lord"), new Track("Sinnerman"), new Track("Isn't it a Pitty"),
                               new Track("I'm feeling good"), new Track("My baby just care for me") } },
                new AlbumViewModel() { Artist = "Ray Charles", Album = "What i'd say",
                    Tracks = { new Track("What i'd say"), new Track("Hit the road Jack"), new Track("I got a woman"),
                               new Track("Let it be"), new Track("Georgia oh my mind") } }
            };

            _selectedAlbum = _albumSet[0];

        }

        private readonly ObservableCollection<AlbumViewModel> _albumSet;
        public ObservableCollection<AlbumViewModel> AlbumSet
        {
            get { SetTrackNumber(); return _albumSet; }
        }

        private AlbumViewModel _selectedAlbum;
        public AlbumViewModel SelectedAlbum
        {
            get => _selectedAlbum;
            set => SetProperty(ref _selectedAlbum, value);
        }

        private AlbumViewModel _toAddAlbum;
        public AlbumViewModel ToAddAlbum
        {
            get => _toAddAlbum;
            set => SetProperty(ref _toAddAlbum, value);
        }

        public void AddAlbum()
        {
            AlbumViewModel album = new AlbumViewModel
            {
                Artist = "John Doe",
                Album = "No name",
                Tracks = { }
            };
            _albumSet.Add(album);
            SelectedAlbum = album;
        }

        public void RemoveAlbum()
        {
            if (_selectedAlbum != null)
            {
                _albumSet.Remove(_selectedAlbum);
                SelectedAlbum = null;
            }
        }

        private void SetTrackNumber()
        {
            foreach (AlbumViewModel album in _albumSet)
            {
                foreach (Track track in album.Tracks)
                {
                    track.Number = album.Tracks.IndexOf(track) + 1;
                }
            }
        }
    }
}
