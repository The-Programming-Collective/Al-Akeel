import Image from 'next/image'
import styles from './page.module.css'

const init = () => {
  fetch('http://localhost:8080/Al-Akeel/api/initialization', {
    method: 'GET'
  }) 
  .then((response) => response.json())
  .then((data) => {
    console.log(data);
  })
  .catch((error) => {
    console.log(error);
  }
  );
}

export default function Home() {
  init();
  return (
    <main className={styles.main}>
      <div className={styles.center}>
        <h1>
          Al-Akeel
        </h1>
      </div>

      <div className={styles.grid}>
        <a
          href="/signup"
          className={styles.card}
          target="_self"
          rel="noopener noreferrer"
        >
          <h2>
            Signup <span>-&gt;</span>
          </h2>
          <p>Create an Akeel account&nbsp;.</p>
        </a>
        <a> </a>
        <a
          href="/login"
          className={styles.card}
          target="_self"
          rel="noopener noreferrer"
        >
          <h2>
            Login <span>-&gt;</span>
          </h2>
          <p>Login to your Akeel account and start ordering.</p>
        </a>
        <a></a>
      </div>
    </main>
  )
}
