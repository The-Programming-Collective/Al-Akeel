"use client";

import React, { useEffect, useRef, useState } from 'react'
import styles from './page.module.css'
import Link from 'next/link';


export default function Page() {
    const [name, setName] = useState('');
    const [userName, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('');

    const handleSubmit = (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        const data = { name, userName, password, role };
        console.log(data);
        fetch('http://localhost:8080/Al-Akeel/api/signup', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        })
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
        })
        .then(() => {
            console.log('success');
            window.location.href = 'http://localhost:3000/';
        })
        .catch((error) => {
            console.log(error);
        }
        );
    }

    const setDisabled = () => {
        let fees = document.getElementById('fees') as HTMLInputElement;
        fees.disabled = true;
    }
    const setEnabled = () => {
        let fees = document.getElementById('fees') as HTMLInputElement;
        fees.disabled = false;
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
                <h1 className={styles.h1}>Sign Up</h1>
                <br></br>
                <form action="/restaurant" method="post" className={styles.form} onSubmit={handleSubmit}>
                    <a className={styles.atag}>
                        Name
                    </a>
                    <input className={styles.input} type="text" name="name" required value={name} onChange={(e) => setName(e.target.value)} />
                    <a className={styles.atag}>
                        Username
                    </a>
                    <input className={styles.input} type="text" name="user" required value={userName} onChange={(e) => setUsername(e.target.value)} />
                    <a className={styles.atag}>
                        Password
                    </a>
                    <input className={styles.input} type="text" name="pass" required value={password} onChange={(e) => setPassword(e.target.value)} />
                    <a className={styles.atag}>
                        Role
                    </a>
                    <select className={styles.input} name="role" value={role} onChange={(e) => setRole(e.target.value)}>
                        <option value='' disabled hidden>Choose Role</option>
                        <option onClick={setDisabled} value="CUSTOMER">Customer</option>
                        <option onClick={setEnabled} value="RUNNER">Runner</option>
                    </select>
                    <a className={styles.atag}>
                        Fees
                    </a>
                    <input id='fees' className={styles.fees} disabled type="text" name="fees"/>
                    <button className={styles.submit} type="submit">Signup</button>
                </form>
            </div>
        </main>
    )

}
