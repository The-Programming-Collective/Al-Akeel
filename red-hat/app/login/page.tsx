"use client";

import React, { useEffect, useState } from 'react'
import styles from './page.module.css'
import Link from 'next/link';


export default function Page() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const auth = 'Basic ' + btoa(username + ':' + password);
    console.log(auth);


    const handleSubmit = (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        handleLogin();
        fetch('http://localhost:8080/Al-Akeel/api/login', {
            method: 'GET',
            headers: {
                accept: '*/*',
                'Content-Type': 'application/json',
                authorization: 'Basic ' + btoa(username + ':' + password)
            },
        })
            .then((response) => response.json())
            .then((data) => {
                if (data.role == 'CUSTOMER') {
                    window.location.href = 'http://localhost:3000/restaurants';
                    console.log('customer');
                }
                else if (data.role == 'RUNNER') {
                    window.location.href = 'http://localhost:3000/runner';
                    console.log('runner');
                }
                else if (data.role == 'RESTAURANT_OWNER') {
                    window.location.href = 'http://localhost:3000/owner';
                    console.log('owner');
                }
            }
            );
    }

    const handleLogin = () => {
        localStorage.setItem('username', JSON.stringify(username));
        localStorage.setItem('password', JSON.stringify(password));
    }


    return (
        <main className={styles.main}>
            <nav className={styles.center}>
                <Link href='/'>
                    <h1>
                        Al-Akeel | Home
                    </h1>
                </Link>
            </nav>
            <div className={styles.div}>
                <h1 className={styles.h1}>Login</h1>
                <br></br>
                <form className={styles.form} onSubmit={handleSubmit}>
                    <a className={styles.atag}>
                        Username
                    </a>
                    <input className={styles.input} type="text" name="user" required value={username} onChange={(e) => setUsername(e.target.value)} />
                    <a className={styles.atag}>
                        Password
                    </a>
                    <input className={styles.input} type="text" name="pass" required value={password} onChange={(e) => setPassword(e.target.value)} />
                    <button className={styles.submit} type="submit">Login</button>
                </form>
            </div>
        </main>
    )
}
